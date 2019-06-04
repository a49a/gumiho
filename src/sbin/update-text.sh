pk_column=1
sed -i 's/\t/\x1/g' origin
update=`sed 's/\t/\x1/g' update`
sentence=""
for new_line in $update
do
    #用``就不行用$()可以
    pk=$(echo $new_line | awk -F '\x1' "{print \$${pk_column}}")
    #TODO 这行待优化，数据量大有问题
    #每循环一次都执行一次sed,需要循环生成一条sed在执行
    sentence=$sentence"s/^${pk}\x1.*$/${new_line}/;"
done
sed -i "{$sentence}" origin