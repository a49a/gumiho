#!/usr/bin/env bash
#根据时间戳增量同步
HOST="localhost"
USER="foo"
PASSWORD="foo"
DB="foo"
TABLE="foo"
updated_at="update_time"

#$1表明,$2增量更新字段
incremental_sync() {
    time=`date +%F -d -1day`
    SQL="SELECT * FROM $1 WHERE $2 > ${time}"
    mysql -h${HOST}-u${USER} -p{PASSWORD} ${DB} -e ${SQL} >> "$1_${time}.csv"
}
#$1库名
get_update_field() {
    SQL="SELECT table_name, column_name FROM information_schema.columns \
    WHERE table_schema=$1 AND \
    column_default='CURRENT_TIMESTAMP' AND \
    extra='on update CURRENT_TIMESTAMP'"
    mysql -h${HOST}-u${USER} -p{PASSWORD} ${DB} -e ${SQL}
}




