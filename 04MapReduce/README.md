## MapReduce 概述
MapReduce是一个分布式运算程序的编程框架，是用户开发"基于Hadoop的数据分析应用"的核心框架
核心功能是将用户编写的业务逻辑代码和自带默认组件整合成一个完整的分布式运算程序，并发运行在一个Hadoop集群上。

### MapReduce 优缺点
优点 
1. 易于编程（简单的实现一些接口，就可以完成一个分布式程序）
2. 良好的扩展性（简单的增加机器）
3. 高容错性 （其中一台节点挂了，它可以把上面的计算任务转移到另外一台节点上运行，不至于这个任务运行失败 不需要人工参于）
4. 适合PB级别以上海量数据的离线处理，提供数据处理的能力

缺点
1. 不擅长实时计算
2. 不擅长流式计算 （输入的数据是静态的）
3. 不擅长DAG（有向图）计算 （每个MapReduce作业的输出结果都会写在磁盘上，会造成大量的磁盘IO，导致属性非常低下）
4. 

### MapReduce 的核心思想
1. MapReduce运算程序一般需要分成2个阶段： Map 阶段和Reduce 阶段
2. Map阶段的并发MapTask，完全并行运行，互不相干
3. Reduce阶段的并发ReduceTask，完全互不相干，但是他们的数据依赖于上一个阶段的所有MapTask并发实例的输出
4. MapReduce 编程模型只能包含一个Map阶段和一个Reduce阶段，如果逻辑复杂那就只能多个MapReduce程序串行运行
![](imgs/001MapReduce编程核心思想.png)

### MapReduce进程
一个完整的MapReduce程序在分布式运行时有三类实例进程：
MrAppMaster 负责整个程序的过程调度及状态协调
MapTask 负责Map阶段的整个数据处理流程
ReduceTask  负责Reduce阶段整个数据处理流程

### 常用数据序列化类型
Java 类型    Hadoop Writable类型
boolean        BooleanWritable
byte            ByteWritable
int             IntWritable
float           FloatWritable
long            LongWritable
double          DoubleWritable
String          Text
map             MapWritable
array           ArrayWritable


### MapReduce编程规范
Mapper 
    用户自定义 Mapper要继承的父类
    Mapper的输入数据是KV对的形式（KV的类型可自定义）
    Mapper中的业务逻辑写在map()方法中
    Mapper的输出数据是KV对的形式（KV的类型可自定义）
    map()方法（MapTask）对每个<K,V>调用一次
Reducer 
    用户自定义 的Reduce要继承自己的父类
    Reducer的输入数据类型对应Mapper的输出数据类型，也是KV 
    Reducer的业务逻辑写在reduce()方法中
    ReduceTask进程对每个相同的k的<k,v> 组调用一次reduce()方法
Driver
    相当于YARN集群的客户端，用于提交我们的整个程序到YARN集群，提交的是封装了MapReduce程序相关运行参数的job对象

### 在集群上运行 Wordcout 
将打好的jar包上传到服务器 运行如下
hadoop jar wc.jar com.huangg.mr.wordcount.WordCountDriver /user/hadoop/input /user/hadoop/wc_output










