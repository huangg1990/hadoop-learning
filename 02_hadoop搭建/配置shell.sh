#bin/bash

# 永久修改主机名
hostnamectl set-hostname hadoop1

# 修改静态IP地址
[root@192 ~]# vim /etc/sysconfig/network-scripts/ifcfg-ens33 
TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
BOOTPROTO=static
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=ens33
UUID=e11d3236-ff59-420e-87a7-43c49e92636b
DEVICE=ens33
ONBOOT=yes

IPADDR=192.168.0.220
NETMASK=255.255.255.0
GATEWAY=192.168.0.1
DNS1=192.168.0.1


# 关闭防火墙
[root@192 ~]# systemctl stop firewalld
[root@192 ~]# systemctl disable firewalld
[root@192 ~]# systemctl status firewalld



# 创建hadoop用户
[root@192 ~]# useradd hadoop
[root@192 ~]# passwd hadoop

# 配制hadoop用户具有root权限
[root@192 ~]# vim /etc/sudoers

## Allow root to run any commands anywhere 
root    ALL=(ALL)       ALL
hadoop  ALL=(ALL)       ALL


#  在/opt目录创创建文件夹
[root@192 ~]# su - hadoop
[hadoop@hadoop1 ~]$ cd /opt/
[hadoop@hadoop1 opt]$ ll
total 0
[hadoop@hadoop1 opt]$ sudo mkdir module software

# 修改module,software文件夹所有者
[hadoop@hadoop1 opt]$ sudo chown -R hadoop:hadoop module/ software/
[hadoop@hadoop1 opt]$ ll
total 0
drwxr-xr-x. 2 hadoop hadoop 6 Aug 27 21:35 module
drwxr-xr-x. 2 hadoop hadoop 6 Aug 27 21:35 software

# 安装JDK
[root@192 software]# tar -zxvf jdk-8u381-linux-x64.tar.gz -C /opt/module/

# 配置环境变量
[root@192 jdk1.8.0_381]# vim /etc/profile
#JAVA_HOME
export JAVA_HOME=/opt/module/jdk1.8.0_381
export PATH=$PATH:$JAVA_HOME/bin:$JAVA_HOME/jre/bin
export CLASSPATH=$CLASSPATH:.:$JAVA_HOME/lib:$JAVA_HOME/jre/lib

[root@192 jdk1.8.0_381]# source /etc/profile

# 测试
[root@192 jdk1.8.0_381]# java -version
java version "1.8.0_381"
Java(TM) SE Runtime Environment (build 1.8.0_381-b09)
Java HotSpot(TM) 64-Bit Server VM (build 25.381-b09, mixed mode)

# 安装Hadoop
[hadoop@hadoop1 software]$ tar -zxvf hadoop-2.10.2.tar.gz -C /opt/module/

# 配置环境变量
[hadoop@hadoop1 hadoop-2.10.2]$ sudo vim /etc/profile
#HADOOP_HOME
export HADOOP_HOME=/opt/module/hadoop-2.10.2
export PATH=$PATH:$HADOOP_HOME/bin
export PATH=$PATH:$HADOOP_HOME/sbin

[hadoop@hadoop1 hadoop-2.10.2]$ source /etc/profile

# 测试
[hadoop@hadoop1 hadoop-2.10.2]$ hadoop
