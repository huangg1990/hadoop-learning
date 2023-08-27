## Hadoop环境搭建
1. 虚拟机环境准备
    1) 克隆虚拟机
    2) 修改克隆虚拟机的静态IP
    3) 修改主机名
    4) 关闭防火墙
    5) 创建hadoop用户
    6) 配制hadoop用户具有root权限
    vim /etc/sudoers 
    hadoop ALL=(ALL) ALL
    7) 在/opt目录创创建文件夹
    sudo mkdir /opt/module /opt/software
    修改module,software文件夹所有者
    sudo chown hadoop:hadoop module/ software/

2. 安装JDK 1.8
    tar -zxvf xxx.tar.gz -C /opt/module/

    配制环境变量
    vim /etc/profile

3. 安装版本 Hadoop 2.10.2
    tar -zxvf xxx.tar.gz -C /opt/module/

    配制环境变量
    bin
    sbin

## Hadoop目录说明
1. bin 目录 执行目录
2. etc 存放的是Hadoop配制文件
3. lib 存放hadoop 的本地库(对数据进行压缩解压功能)
4. sbin 存放启动或停止hadoop相关服务脚本
5. share 存放hadoop的依赖jar包,文档,和官方案例

## Hadoop 运行模式
本地模式,伪分布式模式以及完全分布工模式
1. 本地模式

2. 伪分布式

3. 分布工模式


## HDFS
1. 格式化
    为什么不能一直格式化NameNode,格式化NameNode要注意什么?
    格式化NameNode会产生新的集群ID,导致NameNode和DataNode的集群ID不一致,集群找不到已往数据.所惟格式化NameNode时,一定要先删除DATA数据
    和LOG日志,然后再格式化NameNode
![!\[Alt text\](image.png)](imgs/DataNode-NameNode.png)

2. 基本操作
    1)创建目录
    bin/hdfs dfs -mkdir -p /user/huangg/input
    2) 列出目录
    bin/hdfs dfs -ls /
    bin/hdfs dfs -lsr /
    3) 文件上传
    bin/hdfs dfs -put wcinput/wc.input /user/huangg/input
    http://192.168.0.220:50070/explorer.html#/user/huangg/output

## 配制文件说明
默认配制文件和自定义配制文件,只有用户想修改某一默认配置时,才需要修改自定义配制文件,更改相应属性值
1. 默认配制文件
    要获取的默认文件    默认存放在Hadoop的jar包中的位置
    core-default.xml    hadoop-common-xxx.jar/core-default.xml
    hdfs-default.xml    hadoop-hdfs-xxx.jar/hdfs-default.xml
    yarn-default.xml    hadoop-yarn-common-xxx.jar/yarn-default.xml
    mapred-default.xml  hadoop-mapreduce-client-core-xxx.jar/mapred-default.xml

2. 自定义配置文件
    core-site.xml
    hdfs-site.xml
    yarn-site.xml
    mapred-site.xml 
    这4个配制文件存放在 etc/hadoop/目录下,用户可以根据项目需求重新进行修改配制


# 完全分布式运行模式(开发重点)
1. 准备3台机器(关闭防火墙,静态IP,主机名称)
2. 安装JDK
3. 配置环境变量
4. 安装Hadoop
5. 配置环境变量
6. 配置集群
7. 单点启动
8. 配置ssh
9. 群起并测试集群

## 集群搭建配置
1. 使用命令
scp 命令 全量拷贝
2rsync -rvl source/dir user@host:/dir
主要用户同步 使用 ,只更新有变更的文件

2. 集群部署规划
Hadoop221 NameNode,DataNode,NodeManager
Hadoop222 DataNode,ResourceManager,Nodemanager
hadoop223 SecondaryNameNode,DataNode,NodeManager

3. 配置集群
    1) 核心配制文件 core-site.xml
    <configuration>
        <!-- 指定HDFS中NameNode -->
        <property>
            <name>fs.defaultFS</name>
            <value>hdfs://Hadoop221:9000</value>
        </property>
        <!-- 运行时产生文件的存储目录 -->
        <property>
            <name>hadoop.tmp.dir</name>
            <value>/opt/module/hadoop-2.10.2/data/tmp</value>
        </property>
    </configuration>

    2) HDFS配制文件
    hadoop-env.sh 配制JAVA_HOME export JAVA_HOME=/opt/module/jdk1.8.0_381
    配制hdfs-site.xml
    <configuration>
        <!-- 指定HDFS中副本数量 -->
        <property>
            <name>dfs.replication</name>
            <value>3</value>
        </property>
        <!-- 指定Hadoop辅助名称节点主机配置 -->
        <property>
            <name>dfs.namenode.secondary.http-address</name>
            <value>hadoop223:50090</value>
        </property>
    </configuration>

    3) Yarn配置文件
    yarn-env.sh配置JAVA_HOME export JAVA_HOME=/opt/module/jdk1.8.0_381
    配置yarn-site.xml
    <configuration>
        <!-- Reducer 获取数据的方式 -->
        <property>
            <name>yarn.nodemanager.aux-services</name>
            <value>mapreduce_shuffle</value>
        </property>
        <!-- 指定YARN的ResourceManager的地址 -->
        <property>
            <name>yarn.nodemanager.hostname</name>
            <value>Hadoop222</value>
        </property>
    </configuration>
4. 使用 xsync脚本 同步到每台服务器


格式化 hdfs 
[hadoop@hadoop221 tmp]$ hdfs namenode -format










