#!/usr/bin/env bash

base_dir=$(cd `dirname $0`;pwd)
hdfs_base="hdfs://"
database="foo"
now_day=$(date +"%Y%m%d")
conf="${base_dir}/conf/$1.conf"
update_conf=${base_dir}/conf/update.conf
update_src_dir=${hdfs_base}/ods/${database}/update/${now_day}
old_data_dir=${hdfs_base}/dwd/${database}
data_download_dir=/tmp/etl/${database}
update_download_dir="${data_download_dir}/update"
new_data_upload_dir=${hdfs_base}/tao/dwd/${database}
delimit="\001"
update_file_delimit="\t"
meta_dir="${base_dir}/meta"

download() {
    full_src="${old_data_dir}/$1"
    full_dst="${data_download_dir}/$2"
    update_src="${update_src_dir}/$1"
    update_dst="${update_download_dir}/$2"

}

upload() {

}