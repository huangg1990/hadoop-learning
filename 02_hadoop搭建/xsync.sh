#!/bin/sh

# 获取输入参数个数，如果没有参数，直接退出
pcount=$#
if((pcount!=4)); then
    echo Usage: $0 filename servername startno endno
    exit;
fi


# 获取文件名称
p1=$1
fname=`basename $p1`
echo fname=$fname

# 获取上级目录到绝对路径
pdir=`cd -P $(dirname $p1); pwd`
echo pdir=$pdir
# 获取当前用户名称
user=`whoami`

# 循环
for((host=221; host<=223; host++)); do
    echo $pdir/$fname $user@hadoop$host:$pdir
    echo ==================$slave$host==================
    rsync -rvl $pdir/$fname $user@hadoop$host:$pdir
done



# 如果相民xsync全局可用,可以放在/usr/local/bin 目录下  