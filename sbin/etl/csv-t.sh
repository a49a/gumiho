#!/usr/bin/env bash

pk=1
delimiter="\t"

r=$(awk -F "${delimiter}" 'BEGIN{i='"$pk"'} NR == FNR {arr[$i]=$i} NR != FNR{if($i in arr){print FNR}}')