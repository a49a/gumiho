#!/usr/bin/env bash
source ./util.sh
datax_path=
today=$(date +%Y-%m-%d)
db=$1
table=$2
time_field=$3
if [[ $# != 3 ]]; then
    echo "参数长度为３"
    exit 1
fi

start_job() {
    python ${datax_path}bin/datax.py \
    ${datax_path}job/full_today.json \
    -p "-Dend_time=${today} \
    -Dtable=${table} \
    -Dtime_field=${time_field} \
    -Ddb=${db}"
}


start_job
put