#!/usr/bin/env bash

pk=1
delimiter="\t"
key=4
del_no=$(cat test | awk -F "${delimiter}" "{ if (\$${pk} == ${key}) print NR}")
echo $del_no
get_even_number() {
  origin_no=$1
  if [ $[$origin_no % 2] -eq 0 ];then
    echo $origin_no
  else
    echo $(( origin_no - 1 ))
  fi
}
if [ -z $del_no ]
then
  echo "not find history that the pk is ${key}"
else
  echo "start update test when pk is ${key}......"
  echo "update data start"
  insert_no=`get_even_number $del_no`
  new_line="haha"
  sen="${insert_no}a${key}${delimiter}${new_line}\n"
  sed "1d;4d" test
fi
