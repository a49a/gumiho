#!/usr/bin/env bash
#根据时间戳增量同步
HOST="localhost"
USER="foo"
PASSWORD="foo"
DB="foo"
TABLE="foo"
updated_at="update_time"

#table_name,update_field
incremental_sync() {
    time=`date +%F -d -1day`
    SQL="SELECT * FROM $1 WHERE $2 > ${time}"
    mysql -h${HOST}-u${USER} -p{PASSWORD} ${DB} -e ${SQL} >> "$1_${time}.csv"
}





