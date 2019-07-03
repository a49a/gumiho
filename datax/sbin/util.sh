#!/usr/bin/env bash

local_dir=/data/datax/${db}/
hdfs_dir=hdfs://ha/ods/${db}/

put() {
    hdfs dfs -put -f ${local_dir}${table}__* ${hdfs_dir}${table}.txt
}

append_hdfs() {
    hdfs dfs -appendToFile ${local_dir}${table}__* ${hdfs_dir}${table}.txt
}