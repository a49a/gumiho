pk_column=1
sed -i 's/\t/\x1/g' origin
update=`sed 's/\t/\x1/g' update`
new_lines=($update)
for new_line in ${new_lines[@]}
do
    #用``就不行用$()可以
    pk=$(echo $new_line | awk -F '\x1' "{print \$${pk_column}}")
    sed -i "s/^${pk}\x1.*$/${new_line}/" origin
done