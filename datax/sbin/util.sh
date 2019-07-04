#!/usr/bin/env bash

local_dir=/data/datax/${db}/
hdfs_dir=hdfs://ha/ods/${db}/

HOST=foo
USER=foo
DB=foo
PASSWORD=foo

put() {
    hdfs dfs -put -f ${local_dir}${table}__* ${hdfs_dir}${table}.txt
}

append_hdfs() {
    hdfs dfs -appendToFile ${local_dir}${table}__* ${hdfs_dir}${table}.txt
}

get_conf() {
    SQL="SELECT db, name FROM foo;"
    r=$(mysql -h${HOST} -u${USER} -p${PASSWORD} ${DB} -e "${SQL}" | tr '\t' ',' | sed '1d')
    array=(${r})
    echo ${array[*]}
}

for conf in $(get_conf); do
    a=(${conf//,/ })
    db=${a[0]}
    table=${a[1]}
    echo ${table}
done