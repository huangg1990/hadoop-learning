#### 符合正则表达式'dfs[a-z.]+'的单词个数
[hadoop@hadoop1 hadoop-2.10.2]$ cd /opt/module/hadoop-2.10.2
[hadoop@hadoop1 hadoop-2.10.2]$ mkdir input 
[hadoop@hadoop1 hadoop-2.10.2]$ cp -r etc/hadoop/*.xml input/
[hadoop@hadoop1 hadoop-2.10.2]$ hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.10.2.jar  grep input/ output 'dfs[a-z.]+'


[hadoop@hadoop1 hadoop-2.10.2]$ cd output/
[hadoop@hadoop1 output]$ ll
total 4
-rw-r--r--. 1 hadoop hadoop 11 Aug 27 22:16 part-r-00000
-rw-r--r--. 1 hadoop hadoop  0 Aug 27 22:16 _SUCCESS

#### 官主WordCount案例
[hadoop@hadoop1 ~]$ mkdir hadoop-exam
[hadoop@hadoop1 ~]$ cd hadoop-exam/
[hadoop@hadoop1 hadoop-exam]$ ll
total 0
[hadoop@hadoop1 hadoop-exam]$ mkdir wcinput
[hadoop@hadoop1 hadoop-exam]$ mkdir wcoutput
[hadoop@hadoop1 hadoop-exam]$ cd wcinput/
[hadoop@hadoop1 wcinput]$ ll
total 0
[hadoop@hadoop1 wcinput]$ touch wc.input
[hadoop@hadoop1 wcinput]$ vim wc.input 
hadoop yarn
hadoop mapreduce
huangg
huangg

[hadoop@hadoop1 hadoop-exam]$ cd /home/hadoop/hadoop-exam
[hadoop@hadoop1 hadoop-exam]$ hadoop jar /opt/module/hadoop-2.10.2/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.10.2.jar wordcount wcinput wcoutput                                        

[hadoop@hadoop1 hadoop-exam]$ cd wcoutput/
[hadoop@hadoop1 wcoutput]$ ll
total 4
-rw-r--r--. 1 hadoop hadoop 37 Aug 27 22:23 part-r-00000
-rw-r--r--. 1 hadoop hadoop  0 Aug 27 22:23 _SUCCESS
[hadoop@hadoop1 wcoutput]$ cat part-r-00000 
hadoop  2
huangg  2
mapreduce       1
yarn    1

