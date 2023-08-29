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

    -- 使用这个 上面的启动不了
    <configuration>
        <property>
            <name>yarn.nodemanager.aux-services</name>
            <value>mapreduce_shuffle</value>
        </property>
        <property>
            <name>yarn.resourcemanager.address</name>
            <value>hadoop222:8032</value>
        </property>
        <property>
            <name>yarn.resourcemanager.scheduler.address</name>
            <value>hadoop222:8030</value>
        </property>
        <property>
            <name>yarn.resourcemanager.resource-tracker.address</name>
            <value>hadoop222:8031</value>
        </property>
    </configuration>




4. 使用 xsync脚本 同步到每台服务器

格式化 hdfs 
[hadoop@hadoop221 tmp]$ hdfs namenode -format


5. SSH免密登录
![!\[Alt text\](image.png)](imgs/SSH.png)
生成 ssh-keygen -t rsa
文件说明
known_hosts 记录ssh访问过计算机的公钥(public eky)
id_rsa生成的私钥
id_rsa.pub生成的公钥
authorized_keys 存放授权过的无密码登录服务器公钥


拷贝密钥
ssh-copy-id hadoop221
ssh-copy-id hadoop222
ssh-copy-id hadoop223

6. 群启集群
   1) 配制etc/hadoop/slaves (注意该文件中结尾不允许有空格,文件中不允许有空行)
    hadoop221
    hadoop222
    hadoop223
    同步 xsync slaves
   2) 注意由于配制如下 
    Hadoop221 NameNode,DataNode,NodeManager
    Hadoop222 DataNode,ResourceManager,Nodemanager
    hadoop223 SecondaryNameNode,DataNode,NodeManager
    在启动hdfs时需要在 221上启动 sbin/start-dfs.sh
    在启动yarn时需要在 222上启动 (因为ResourceManager 在222上)

    3) ## 基本测试
    hdfs dfs -put wcinput/wc.input /
    hdfs dfs -put /opt/software/hadoop.xxx.gz

    4) 启动总结
    分别启动hadoop-daemon.sh star/stop namenode/datanode
    分别启动yarn yarn-daemon.sh start/stop resourcemanager/nodemanager
    各模块启动(常用)
    sbin/start-dfs.sh 
    sbin/start-yarn.sh

    注意:NameNode和ResourceManager如果不是同一台机器,不能在NameNode上启动YARN,应该在RemorceManager所在的机器上启动YARN

7. 集群时间同步
    1) 安装ntp
    2) 修改ntp配置文件
        授权 网段上的所有机器可以从这台机器上查询和同步时间
        集群在局域网中,不使用其他互联网上的时间
        当该节点丢失网络链接,依然可以采用本地时间作为时间服务器为集群提供时间同步
        [root@hadoop221 ~]# vim /etc/ntp.conf
        restrict 192.168.0.0 mask 255.255.255.0 nomodify notrap
        # server 0.centos.pool.ntp.org iburst
        # server 1.centos.pool.ntp.org iburst
        # server 2.centos.pool.ntp.org iburst
        # server 3.centos.pool.ntp.org iburst
        server 127.127.0.1
        fudge 127.127.1.0 stratum 10




    3) 修改/etc/sysconfig/ntpd文件 (让硬件时间与系统时间一起同步)
        [root@hadoop221 ~]# vim /etc/sysconfig/ntpd
        SYNC_HWCLOCK=yes
    4) 重新启动ntpd服务 
        [root@hadoop221 ~]# systemctl restart ntpd
        [root@hadoop221 ~]# systemctl status ntpd
    5) 设置ntpd服务开机启动
        [root@hadoop221 ~]# systemctl enable ntpd
        
        [root@hadoop222 ~]# crontab -e 
        no crontab for root - using an empty one
        */1 * * * * /usr/sbin/ntpdate hadoop221










