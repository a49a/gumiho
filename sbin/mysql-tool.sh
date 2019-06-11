#!/usr/bin/env bash
#根据时间戳增量同步
HOST="localhost"
USER="foo"
PASSWORD="foo"
DB="foo"
TABLE="foo"
updated_field="update_time"

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

get_table_size() {
    SQL="SELECT TABLE_NAME, \
    concat(truncate(DATA_LENGTH/1024/1024/1024, 4), 'GB') as data_size, \
    concat(truncate(INDEX_LENGTH/1024/1024/1024, 4), 'GB') as idx_size \
    FROM information_schema.TABLES \
    order by DATA_LENGTH desc;"
    mysql -h${HOST}-u${USER} -p{PASSWORD} ${DB} -e ${SQL}
}
