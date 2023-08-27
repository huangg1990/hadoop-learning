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




