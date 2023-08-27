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



启动Yarn并运行MapReduce程序
1. 配制集群在YARN上运行MR
[hadoop@hadoop1 hadoop]$ vim yarn-env.sh 
export JAVA_HOME=/opt/module/jdk1.8.0_381


[hadoop@hadoop1 hadoop]$ vim yarn-site.xml 
<configuration>
	<!-- Reducer 获取数据的方式 -->
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
    <!-- 指定YARN的ResourceManager的地址 -->
    <property>
        <name>yarn.nodemanager.hostname</name>
        <value>hadoop1</value>
    </property>
</configuration>

[hadoop@hadoop1 hadoop]$ vim mapred-env.sh 
export JAVA_HOME=/opt/module/jdk1.8.0_381


[hadoop@hadoop1 hadoop]$ vim mapred-site.xml
<configuration>
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property> 
</configuration>

2. 启动、测试集群、增、删、查
[hadoop@hadoop1 hadoop-2.10.2]$ sbin/yarn-daemon.sh start resourcemanager
[hadoop@hadoop1 hadoop-2.10.2]$ sbin/yarn-daemon.sh start nodemanager
[hadoop@hadoop1 hadoop-2.10.2]$ jps -l
12930 sun.tools.jps.Jps
12550 org.apache.hadoop.yarn.server.resourcemanager.ResourceManager
1911 org.apache.hadoop.hdfs.server.datanode.DataNode
1820 org.apache.hadoop.hdfs.server.namenode.NameNode
12798 org.apache.hadoop.yarn.server.nodemanager.NodeManager

界面查看
http://192.168.0.220:8088/cluster

测试 (删除之前的目录)
[hadoop@hadoop1 hadoop-2.10.2]$ hdfs dfs -rm -r /user/huangg/output
# 启动wordcount程序 
[hadoop@hadoop1 hadoop-2.10.2]$ hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.10.2.jar wordcount /user/huangg/input /user/huangg/output
# 在web界面查看


# 配制历史服务器
[hadoop@hadoop1 hadoop-2.10.2]$ vim etc/hadoop/mapred-site.xml
<configuration>
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property> 
    <!-- 配制历史服务器 -->
    <property>
        <name>mapreduce.jobhistory.address</name>
        <value>hadoop1:10020</value>
    </property>
     <property>
        <name>mapreduce.jobhistory.webapp.addresss</name>
        <value>hadoop1:19888</value>
    </property>
</configuration>

# 启动历史服务器
[hadoop@hadoop1 hadoop-2.10.2]$ sbin/mr-jobhistory-daemon.sh start historyserver
# 查看JobHistory
[hadoop@hadoop1 hadoop-2.10.2]$ jps -l
13460 sun.tools.jps.Jps
12550 org.apache.hadoop.yarn.server.resourcemanager.ResourceManager
1911 org.apache.hadoop.hdfs.server.datanode.DataNode
13387 org.apache.hadoop.mapreduce.v2.hs.JobHistoryServer


# 配制日志的聚集
应用 运行完成以后,将程序运行晶志信息上传到HDFS系统上.
好处:可以方便的查看到程序运行详情,方便开发测试
开启晶志聚集功能,需要重新启动NodeManager,ResourceManager和HistoryManager

开启步骤如下

	<!-- 日志的聚集 -->
 	<property>
        <name>yarn.log-aggregation-enable</name>
        <value>true</value>
    </property>
    <!-- 日志保留时间设置7天 -->
     <property>
        <name>yarn.log-aggregation.retain-seconds</name>
        <value>604800</value>
    </property>


[hadoop@hadoop1 hadoop-2.10.2]$ sbin/mr-jobhistory-daemon.sh stop historyserver
stopping historyserver
[hadoop@hadoop1 hadoop-2.10.2]$ sbin/yarn-daemon.sh stop nodemanager
stopping nodemanager
[hadoop@hadoop1 hadoop-2.10.2]$ sbin/yarn-daemon.sh stop resourcemanager
stopping resourcemanager


sbin/yarn-daemon.sh start resourcemanager  &&\
sbin/yarn-daemon.sh start nodemanager  &&\
sbin/mr-jobhistory-daemon.sh start historyserver

# 测试 删除原有Wordcount 结果目录
[hadoop@hadoop1 hadoop-2.10.2]$ hdfs dfs -rm -r /user/huangg/output
[hadoop@hadoop1 hadoop-2.10.2]$ hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.10.2.jar wordcount /user/huangg/input /user/huangg/output



