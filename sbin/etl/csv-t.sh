#!/usr/bin/env bash

pk=1
delimiter="\t"
cleaned_file="cleaned"
update_file="ccc"
update_count=$(cat update_file | wc -l 2>/dev/null)
if [ $update_count -eq 0 ]
then
    echo "A"
else
    echo "B"
fi
#r=$(awk -F "${delimiter}" 'BEGIN{i='"$pk"'} NR == FNR {arr[$i]=$i} NR != FNR{if($i in arr){print FNR}}')
#直接打印行
awk -F "${delimiter}" 'BEGIN{i='"$pk"'} NR == FNR {arr[$i]=$i} NR != FNR{if($i in arr){print}}' | tr "${delimiter}" '\001' > ${cleaned_file}
