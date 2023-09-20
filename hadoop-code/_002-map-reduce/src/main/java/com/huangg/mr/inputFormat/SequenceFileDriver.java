package com.huangg.mr.inputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;


public class SequenceFileDriver {
    public static void main(String[] args) throws Exception{
        if(args==null || args.length==0){
            args = new String[]{
                    "/Users/huanggang/gitroot/data/hadoop-learning/data/wc_input/",
                    "/Users/huanggang/Downloads/wc_my",
            };
        }

        Configuration conf = new Configuration();
        // 设置分隔符号
        conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR," ");

        // 1 获取Job对象
        Job job = Job.getInstance(conf);

        // 设置输入格式
        job.setInputFormatClass(MyFileInputFormat.class);
        // 设置输出格式
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        // 2 设置Jar存储位置
        job.setJarByClass(SequenceFileDriver.class);

        // 3 关联Map和Reduce类
        job.setMapperClass(SequenceFileMapper.class);
        job.setReducerClass(SequenceFileReducer.class);

        // 4 设置Mapper阶段输出数据的Key和value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);

        // 5 设置最终数据输出的key和value 类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BytesWritable.class);

        // 6 设置输入路径和输出路径

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 7 提交Job
//        job.submit();
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
