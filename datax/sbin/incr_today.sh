#!/usr/bin/env bash
source ./util.sh
datax_path=/ddhome/bin/datax/
today=$(date +%Y-%m-%d)
yesterday=$(date -d yesterday +%Y-%m-%d)

start_time=${yesterday}
end_time=${today}
job_conf="time_rds"
db=$1
table=$1
time_field=$2


start_datax_job() {
    python ${datax_path}bin/datax.py \
    ${datax_path}job/${job_conf}.json \
    -p "-Dstart_time=${start_time} \
    -Dend_time=${end_time} \
    -Dtable=${table} \
    -Dtime_field=${time_field} \
    -Ddb=${db}"
}



start_datax_job
append_hdfs