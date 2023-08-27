#bin/bash
1. 修改配制
[hadoop@hadoop1 hadoop]$ vim core-site.xml 

<configuration>
	<!-- 指定HDFS中NameNode -->
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://hadoop1:9000</value>
    </property>
    <!-- 运行时产生文件的存储目录 -->
    <property>
        <name>hadoop.tmp.dir</name>
        <value>/opt/module/hadoop-2.10.2/data/tmp</value>
    </property>
</configuration>


[hadoop@hadoop1 hadoop]$ vim hdfs-site.xml 

<configuration>
	<!-- 指定HDFS中副本数量 -->
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>
</configuration>


2. 配置 hadoop-env.sh
[hadoop@hadoop1 hadoop]$ vim hadoop-env.sh 
export JAVA_HOME=/opt/module/jdk1.8.0_381

3. 启动集群
	1) 格式化 NameNode (第一次启动格式化,以后就不需要了)
	bin/hdfs namenode -format
	2) 启动NameNode
	sbin/hadoop-daemon.sh start namenode
	3) 启动DataNode
	sbin/hadoop-daemon.sh start datanode
	4) 查看集群是否启动成功 
	jps -l 
	

4. hdfs
# 创建目录
bin/hdfs dfs -mkdir -p /user/huangg/input
# 列出目录
bin/hdfs dfs -ls /
bin/hdfs dfs -lsr /

# 文件上传
bin/hdfs dfs -put wcinput/wc.input /user/huangg/input

# 执行wordCount
[hadoop@hadoop1 hadoop-2.10.2]$ bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.10.2.jar wordcount /user/huangg/input /user/huangg/output
# 查看结果
[hadoop@hadoop1 hadoop-2.10.2]$ bin/hdfs dfs -cat /user/huangg/output/p*
hadoop  2
huangg  2
mapreduce       1
yarn    1


