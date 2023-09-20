package com.huangg.mr.nline;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * 在集群上运行
 * hadoop jar wc.jar com.huangg.mr.wordcount.WordCountDriver /user/hadoop/input /user/hadoop/wc_output
 *
 */
public class NlineDriver {

    public static void main(String[] args) throws Exception {

        //2023-09-19 21:30:26,540   INFO  [org.apache.hadoop.mapreduce.JobSubmitter]    -   number of splits:4
        // 默认一个文件一个分片
        if(args==null || args.length==0){
            args = new String[]{
                    "/Users/huanggang/gitroot/data/hadoop-learning/data/wc_input/input.txt",
                    "/Users/huanggang/Downloads/wc_output",
            };
        }

        Configuration conf = new Configuration();
        // 1 获取Job对象
        Job job = Job.getInstance(conf);
        // 2 设置Jar存储位置
        job.setJarByClass(NlineDriver.class);

        // 使用NLineInputFormat处理记录数
        job.setInputFormatClass(NLineInputFormat.class);

        // 2023-09-20 22:08:59,583   INFO  [org.apache.hadoop.mapreduce.JobSubmitter]    -   number of splits:4
        //  number of splits:4 按行切片了
        // 将文件每3行 划分一个切片
        NLineInputFormat.setNumLinesPerSplit(job,3);

        // 3 关联Map和Reduce类
        job.setMapperClass(NlineMapper.class);
        job.setReducerClass(NlineReducer.class);

        // 4 设置Mapper阶段输出数据的Key和value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 5 设置最终数据输出的key和value 类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 6 设置输入路径和输出路径

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 7 提交Job
//        job.submit();
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);

    }
}
