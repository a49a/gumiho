#!/usr/bin/env bash

base_dir=$(cd `dirname $0`;pwd)
hdfs_base="hdfs://"
database="foo"
now_day=$(date +"%Y%m%d")
conf="${base_dir}/conf/$1.conf"
update_conf=${base_dir}/conf/update.conf
update_src_dir=${hdfs_base}/ods/${database}/update/${now_day}
full_dir=${hdfs_base}/dwd/${database}
full_tmp_dir=/tmp/etl/${database}
update_tmp_dir="${data_download_dir}/update"
new_data_upload_dir=${hdfs_base}/tao/dwd/${database}
delimiter="\001"
old_delimiter="\t"
meta_dir="${base_dir}/meta"

download() {
    full_hdfs_path="${full_dir}/$1"
    full_tmp_path="${full_tmp_dir}/$2"
    update_hdfs_path="${update_src_dir}/$1"
    update_tmp_path="${update_tmp_dir}/$2"
    if [[ ! -d "${full_tmp_dir}" ]]; then
        mkdir -p ${full_tmp_dir}
    fi
    if [ ! -d "${update_tmp_dir}" ]; then
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
    hdfs dfs -cat ${update_hdfs_path} | tr "${old_delimiter}" "${delimiter}" > ${update_tmp_path}
}

upload() {

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