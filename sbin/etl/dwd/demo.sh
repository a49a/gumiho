base_dir=$(cd `dirname $0`;pwd)
meta_dir="${base_dir}/meta"
is_first=`cat ${meta_dir}/is_first | grep 1`
# 首次更新
if [[ ${is_first} == 1 ]]; then
    echo "is first"
fi