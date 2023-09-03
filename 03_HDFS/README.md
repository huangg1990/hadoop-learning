# HDFS 概述
1. 背景 在一个操作系统下存储不了所有数据,那么就分配到更多的操作系统中的磁盘,但不方便维护管理,迫切需要一种系统来管理多台机器上的文件,这就是分布式式
文件管理系统,HDFS只是分布式文件管理系统中的一种.

2. Hadoop Distributed File System 它是一个文件系统,用于存储文件,通过目录树来定位文件,其次它是分布式的,由很多服务器来实现其功能,集群中的服务器有各息的角色

3. 使用场景:适合一次写入,多次读出场景,且不支持文件修改,适合做数据分析不适合做网盘

# HDFS 优缺点
1. 优点 
    1) 高容错性 - 数据自动保存我个副本,它通过增加副本的形式,提高容错性. 某一个副本丢失后,它可以自动恢复
    2) 适合处理大数据 - 数据规模:能够处理数据规模达到GB,TB 甚至PB级别的数据;
    3) 文件规模 - 能哆处理百万规模以上的文件数量
    4) 可以构建在廉价的机器上,通过多副本机制,提高可靠性
2. 缺点
    1) 不适合低延时数据访问,比如毫秒级的存储数据,是做不到的
    2) 无法高效的对大量小文件进行存储
        存储大量小文件的话,它会占用NameNode大量的内存来存储文件目录和块信息.这样不可取,因为NameNode的内存总是有限的
        小文件存储的寻址会起过读取时间,它违反了HDFS的设计目标
    3) 不支持并发写入,文件随机修改
        一个文件只能有一个写,不允许多个线程同时写
        仅支持文件Append(追加),不支持文件的随机修改

# HDFS 组成架构
    1) NameNode(MN) 就是Master,它是一个管理者
        管理HDFS的名称空间
        配置副本策略
        管理数据块(Block)映射信息
        处理客户端读取请求
    2) DataNode 就是Slave, NameNode下达命令,DataNode执行实际操作.
        存储实际的数据块
        执行数据块的读/写操作
        ![Alt text](imgs/hdfs-%E7%BB%84%E6%88%90%E6%9E%B6%E6%9E%84.png)
    3) Client 就是客户端
        文件切分,文件上传HDFS的时候,Client将文件切分成一个个的Block,然后进行上传
        与NameNode交互,获取文件的位置信息
        与DataNode交互,读取或者写入数据
        Client提供一些命令来管理HDFS,比如NameNode格式化
        Client可能通过一些命令来访问HDFS,比如对HDFS增加删除文件操作
    4) SecondaryNameNode 并非NameNode的热备,当NameNode挂掉的时候,它并不能马上替换NameNode提供服务
        辅助NameNode,分担其工作量,比如定期合并Fsimage和Edits,并推送级NameNode
        在紧急情况下,可辅助恢复NameNode
    
# HDFS 文件块大小
HDFS的文件在物理上是分块存储的(Block),块的大小可以通过配置参数(dfs.blocksize)来规定,默认大小在Hadoop 2.x版本中是128MB,老版本是64MB 
![Alt text](imgs/hdfs-%E5%9D%97%E5%A4%A7%E5%B0%8F.png)
HDFS块的大小是由硬盘的 读取速度决定,如果 是SSD 可以设置为256MB 如果是 Raid 可以更高
Tips:
    1) HDFS的块设置太小,会增加寻址时间,程序一直在找块的开始位置
    2) 如果块设置的太大,从磁盘传输数据的时间会明显大于定位这个块开始位置所需要的时间,导致程序在处理这块数据时会非常慢.
总之:HDFS块的大小设置主要取决于硬盘传输速率


# HDFS 的Shell操作
1. 基本语法
    bib/hadoop fs 具体命令 OR bin/hdfs dfs 具体命令
    命令大全 bib/hadoop fs
    hadoop fs -help
    帮助命令 [root@hadoop221 ~]# hadoop fs -help rm
    列出文件 [root@hadoop221 ~]# hadoop fs -ls /    
    列出文件 [root@hadoop221 ~]# hadoop fs -lrs / 
    创建目录 hadoop fs -mkdir -p /huangg/huangg
    从本地剪切粘贴到HDFS hadoop fs -moveFromLocal xx.txt /huangg/huagg/ # 地址会删除
    追加一个文件到已经存在的文件末尾 hadoop fs -appendToFile xx1.txt /huangg/huangg/xx.txt
    查看文件内容
    hadoop fs -cat /huangg/huagg/test.txt

    修改文件所有者权限[-chgrp  -chmod  -chown]
    hadoop fs -chgrp huangg /huangg/t1.txt

    从本地文件系统中拷贝文件到HDFS
    hadoop fs -copyFromLocal local.txt /huangg/huangg/t2.txt

    从HDFS中拷贝文件到本地文件系统
    Hadoop fs -copyToLocal /huangg/huangg/t2.txt local.txt

    从HDFS中拷贝文件到HDFS的其它位置
    hadoop fs -cp /huangg/huangg/t2.txt /huangg/liu/t2.txt

    从HDFS中剪切文件到HDFS的其它位置
    hadoop fs -mv /huangg/huangg/t2.txt /huangg/liu/t2.txt


    从HDFS中下载文件到本地文件系统
    Hadoop fs -get /huangg/huangg/t2.txt local.txt


    从HDFS中合并下载文件到本地文件系统
    Hadoop fs -getmerge /huangg/huangg/* merge.txt


    从本地文件系统上传文件到HDFS
    Hadoop fs -put local.txt /huangg/huangg/t2.txt

    显示文件的末尾
    Hadoop fs -tail /huangg/huangg/t2.txt


    删除文件或文件夹
    Hadoop fs -rm /huangg/huangg/t2.txt
    Hadoop fs -rm -R /huangg/huangg/t2.txt


    删除空目录
    Hadoop fs -rmdir /huangg/huangg

    统计文件夹大小信息
    Hadoop fs -du -h -s /huangg/huangg
2. 设置HDFS中文件的副本数量
    hadoop fs -setrep 3 /huangg/huangg/t2.txt
    设置的副本只是记录在NameNode的元数据中,是否真的会有这么多副本,需要看DataNode的数量,因为目录只有3个DataNode最多也只会存储3份,当再增加DataNode后会自动拷贝一份副本在新的节点 直接满足副本的数量为止.

3. HDFS客户端操作

4. 参数优先级
    1） 客户端代码设置的值
    2） ClassPath下的用户自定义配置文件
    3） 然后是服务器的默认配置


5. HDFS的I/O流操作
    我们可以采用IO流的方式来实现数据的上传和下载



 
