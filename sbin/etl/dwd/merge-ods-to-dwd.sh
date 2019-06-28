#!/usr/bin/env bash

base_dir=$(cd `dirname $0`;pwd)
hdfs_base="hdfs://"
database=${1}
now_day=$(date +"%Y%m%d")
conf="${base_dir}/conf/$1.conf"
table_conf=${base_dir}/conf/table.conf
update_hdfs_ods_dir=${hdfs_base}/ods/${database}/update/${now_day}
full_dir=${hdfs_base}/dwd/${database}
full_tmp_dir=/tmp/etl/${database}
update_tmp_dir="${data_download_dir}/update"
delimiter="\001"
old_delimiter="\t"
meta_dir="${base_dir}/meta"

is_first=`cat ${meta_dir}/is_first | grep 1`
# 首次更新
if [[ ${is_first} == '1' ]]; then
    delimiter="\t"
    full_dir=${hdfs_base}/tao/ods/${database}/full
    echo "正在进行${database}首次更新..."
fi

if [[ ! -d "${meta_dir}" ]]; then
    mkdir -p ${meta_dir}
fi

download() {
    full_hdfs_path="${full_dir}/$1"
    full_tmp_path="${full_tmp_dir}/$2"
    update_hdfs_path="${update_src_dir}/$1"
    update_tmp_path="${update_tmp_dir}/$2"
    if [[ ! -d "${full_tmp_dir}" ]]; then
        mkdir -p ${full_tmp_dir}
    fi
    if [[ ! -d "${update_tmp_dir}" ]]; then
        mkdir -p ${update_tmp_dir}
    fi
    if [[ -f "${full_tmp_path}" ]];then
        echo '' > ${full_tmp_path}
        mv ${full_tmp_path} /tmp/${2}
    fi
    if [[ -f "${update_tmp_path}" ]];then
        echo '' > ${update_tmp_path}
        mv ${update_tmp_path} /tmp/${2}
    fi
    echo "download file from ${full_hdfs_path} to local path ${full_tmp_path} ..."
    hdfs dfs -getmerge ${full_hdfs_path}  ${full_tmp_path}
    echo "download update file from ${update_hdfs_path} to local path ${update_tmp_path} ..."
    if [[ ${is_first} == '1' ]]; then
        #首次更新
        hdfs dfs -get ${update_hdfs_path} ${update_tmp_path}
    else
        #非首次更新
        hdfs dfs -cat ${update_hdfs_path} | tr "${old_delimiter}" "${delimiter}" > ${update_tmp_path}
    fi
}

upload() {
    full_file=$1
    merged_local_file="$1_merged"
    full_file_name=$(basename ${full_file})
    hdfs_file="${full_dir}/upload_tmp_${full_file_name}"
    echo "start upload local file ${merged_local_file} to ${hdfs_file} ..."
    hdfs dfs -put ${merged_local_file} ${hdfs_file}
    if [[ $? == "0" ]]; then
        hdfs dfs -test -e ${full_dir}/${full_file_name}
        if [[ $? == "0" ]]; then
            hdfs dfs -test -e "${full_dir}/${full_file_name}.bak"
            if [[ $? == "0" ]]; then
                hdfs dfs -rm "${full_dir}/${full_file_name}.bak"
            fi
            hdfs dfs -mv "${full_dir}/${file_name}" "${full_dir}/${full_file_name}.bak"
        fi
        hdfs dfs -mv "${remote_file}" "${full_dir}/${full_file_name}"
    fi
}

clean_tmp() {
    full_tmp_path="${full_dir}/${1}"
    update_tmp_path="${update_tmp_dir}/${1}"
    merged_file="${full_tmp_path}_merged"
    echo "表${1}处理完成，开始清理环境..."
    if [[ -f "${full_tmp_path}" ]];then
        echo '' > ${full_tmp_path}
        mv ${full_tmp_path} /tmp/${1}
    fi
    if [[ -f "${update_tmp_path}" ]];then
        echo '' > ${update_tmp_path}
        mv ${update_tmp_path} /tmp/${1}
    fi
    if [[ -f "$merged_file" ]]; then
        echo '' > ${merged_file}
        mv ${merged_file} /tmp/${1}
    fi
}

# 判断文件最后一行是否有换行
has_LF_at_last() {
    file_path=${1}
    last_line=`get_last_line_from_file ${file_path}`
    # 空文件默认认为它有换行符
    if [[ -z ${last_line} ]]; then
        echo "true"
    else
        res=`tail -n 1 ${file_path} | wc -l`
        if [[ ${res} == "1" ]]; then
            echo "true"
        else
            echo "false"
        fi
    fi
}

get_tables() {
    tables=$(cat ${table_conf} | awk -F '=' '{print $1}')
    echo $tables
}

# 根据表名从update_conf文件获取主键索引（从1开始)
get_pk() {
    table_name=$1
    echo $(cat ${table_conf} | awk -F '=' '{ if( $1=="'"$table_name"'" ) print $2}')
}

# 获取文件最后一行
get_last_line() {
    file_path=$1
    echo $(tail -n 1 ${file_path})
}

update_tables() {
    current_date=`date +"%Y-%m-%d %H:%M:%S"`
    hdfs dfs -test -e ${update_hdfs_ods_dir}
    if [[ $? != 0 ]]; then
        echo "${current_date} :${now_day} ods updated data have not extracted complete!"
        exit 1
    fi
    current_day=$(cat ${meta_dir}/current_day | grep ${now_day})
    if [[ -z ${current_day} ]]; then
        echo "${current_date} :${now_day} dwd merged complete or be merging"
        exit 1
    fi
    echo "${now_day}" > ${meta_dir}/current_day
    start_time=$(date +"%s")
    tables=get_tables
    for table in ${tables}; do
        current_date=`date +"%Y-%m-%d %H:%M:%S"`
        echo "start update ${table} ... "
        one_file_start_time=$(date +"%s")
        pk=$(get_pk ${table})
        full_table_files="{${table}.txt,${table}_2019*}"
        download ${full_table_files} ${table}
        full_file=${full_tmp_dir}/${table}
        update_table=${update_tmp_dir}/${table}
        merged_path="${full_file}_merged"
        update_row_count=$(cat ${update_table} | wc -l 2>/dev/null)
        echo "${current_date} :${table}表的${now_day}更新文件中有${update_row_count}条记录.."
        if [[ ${update_row_count} != 0 ]]; then

            awk -F "${delimiter}" 'BEGIN{
                i="'"${pk}"'"
            }
            NR==FNR{
                set[$i]=$i
            }
            NR!=FNR{
                if($i in set)
                {print}
            }' ${update_table} ${full_table} | tr '\t' '\001' > ${merged_path}

            #补全最后一行没有换行情况
            has_LF=$(has_LF_at_last ${merged_path})
            if [[ ${has_LF} == "false" ]]; then
                echo "" >> ${merged_path}
            fi

            cat ${update_table} | tr '\t' '\001' >> ${merged_path}

            if [[ $? != 0 ]]; then
                echo "" > ${meta_dir}/update_deadline_date
                exit 1
            fi
            upload ${full_file} ${merged_file}
        else
            echo "update file is empty..."
            if [[ -z ${is_first} ]]; then
                echo "is first update"
                cat ${full_file} | tr '\t' '\001' > ${merged_path}
                upload ${full_file} ${merged_file}
            fi
        fi

        one_file_end_time=$(date +"%s")
        one_file_using_sec=$((one_file_end_time - one_file_start_time))
        one_file_using_time=$(get_using_time ${one_file_using_sec})
        wait
        clean_tmp ${table}
        echo ""
    done
    end_time=$(date +"%s")
    use_sec=$(( end_time - start_time ))
    use_time=$(get_using_time ${use_sec})
    echo "${use_time}"
}

get_using_time() {
    time_diff=$1
    use_hour=$[ time_diff / 3600 ]
    use_hour_remainder=$[ time_diff % 3600 ]
    use_minute=$[ use_hour_remainder / 60 ]
    use_second=$[ use_hour_remainder % 60 ]
    echo "${use_hour}小时${use_minute}分钟${use_second}秒"
}
