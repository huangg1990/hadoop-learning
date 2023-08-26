# hadoop-learning

## 大数据
Big Data 指无法在一定时间范围内用常规软件工具进行捕捉、管理和处理数据集合,是需要新处理模式才能具有更强的决策力,洞察发现流程和优化能力的海量,
高增长率和多样化的信息资产.

主要解决,海量数据的存储和海量数据的分析

1. 数据存储单位 
    bit,Byte,KB,MB,GB,TB,PB,EB,ZB,YB,BB,NB,DB
    1Byte = 8 bit
    1KB = 1024 Byte
    1MB = 1024 KB
    1G = 1024 MB
    1T = 1024G 
    1P = 1024T

2. 大数据的特点
    1) Volume 大量
    2) Velocity 高速
    3) Variety 多样
    4) Value 低价值密度

3. 大数据应用场景
    1) 物流仓储
    2) 零售
    3) 旅游
    4) 商品广告推荐
    5) 保险
    6) 金融
    7) 房产
    8) 人工智能

4. 大数据的发展前景

5. 大数据部门业务流程分析
    产品人员提需求  (统计总用户数、日活跃用户数,回流用户数)
    数据部门搭建数据平台,分析数据指标
    数据可视化(报表展示,邮件发送,大屏展示等)

6. 大数据部门组织结构
![Alt text](image/0001%E5%A4%A7%E6%95%B0%E6%8D%AE%E9%83%A8%E9%97%A8%E7%BB%84%E7%BB%87%E7%BB%93%E6%9E%84.png)


## Hadoop框架-大数据生态
1. Hadoop
    是一个由Apache基金会所开发的分布式系统基础架构
    主要解决,海量数据的存储和海量数据的分析计算问题.
    广义来说,Hadoop通常是指一个更广泛的开概念--Hadoop生态圈
![Alt text](image/0002Hadoop%E7%94%9F%E6%80%81%E5%9C%88.png)

2. 发展历史
    1) Lucene框架是Doug Cutting开创的开源的罗伦的,用Java书写代码,实现与Google类似的全文索引功能,它提供了全文检索引擎架构,包括完整的查询引擎和索引引擎.
    2) 20001 年年底Lucene成为Apache基金分的一个子项目
    3)对于海量数据的场景,Lucene页面与Google同样的困难,存储数据困难,检索速度慢
    4) 学习和模仿Google解决这些问题的办法:微型版Nutch
    5) 可以说Google是hadoop的思想之源(Google在大数据方面的三篇论文)
        GFS -- HDFS
        Map- Reduce -- MR
        BigTable -- HBase
    6) 2003-4年,Google公司公开了部分GFS和MapReduce思想的细节,以为基础Doug Cutting等人用了2年业余时间实现了DFS和MapReduce机制,使用Nutch性能飙升
    7) 2005年Hadoop作为Lucene的子项目Netch的一部分正式引入Apache基金会
    8) 2006年3月MapReduce和Nutch Distributed File System(NDFS)分别被纳入称为Hadoop的顶级项目中
    9)名字来源的于Doug Cutting儿子的玩具大象
    10) Hadoop就此诞生并迅速发发展,标志着大数据时代来临

3. Hadoop的三大发行版本
    Hadoop版本:Apache,Cloudera,Hortonwords
    Apache版本最原始(最基础)的版本,对于入门学习最好
    Cloudera大大型公司互联网企业中用得较多
    Hortonwords 的文档较好

4. Hadoop的优势
    1) 高可靠性: 底层维护多个数据副本,所以即使Hadoop某个计算元素或存储出现故障,也不会导致数据的丢失
    2) 高扩展性: 在集群间分配任务数据,可言便的扩展数据以千计的节点
    3) 高效性: 在MapReduce的思想下,Hadoop是并行工作的,以加快任务处理速度
    4) 高容错性: 能够自动将失败的任务重新分配

5. Hadoop组成
    Hadoop1.x (HDFS 数据存储,Common辅助工具,MapReduce计算+资源高度)
    Hadoop2.x (HDFS 数据存储,Common辅助工具,MapReduce计算, Yarn资源高度)
    1) HDFS架构概述
        NameNode(nn)存储文件的元数据,如文件名,文件目录结构,文件属性(生成时间,副本数,文件权限),以及每个文件的块列表和块所在的DataNode
        DataNode(dn) 在本地文件系统存储文件块数据,以及块数据的校验
        Secondary(2nn) 用来监控HDFS状态的辅助后台程序,每隔一段时间获取HDFS元数据的快照
    2) Yarn架构
        Resource Manager (RM) 处理客户端请求 管理CPU、内存、磁盘,监控NodeManager,启动或监控ApplicationMaster ,资源分配和调度
        Node Manager(NM) 管理耽搁节点上的资源,处理来源ResourceManager的命令,处理来息ApplicationMaster命令
    3) ApplicationMaster(AM) 负责数据的切分,为应用程序申请资源并分配内部的任务,任务的监控与容错
    4) Container 是YARN中的资源抽象,它封装了某个节点上的多维度资源,如内存,CPU,磁盘,网络等
![Alt text](image/0003YARN%E6%9E%B6%E6%9E%84.png)










































