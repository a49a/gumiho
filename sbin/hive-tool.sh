#!/usr/bin/env bash
beeline -u jdbc:hive2://localhost:10000 -n foo -p foo -e << EOF
    LOAD DATA LOCAL INPATH '' OVERWRITE INTO TABLE foo
EOF