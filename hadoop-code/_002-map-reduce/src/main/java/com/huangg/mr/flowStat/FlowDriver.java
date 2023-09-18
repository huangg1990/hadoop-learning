package com.huangg.mr.flowStat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class FlowDriver {
    public static void main(String[] args) throws Exception {

        args = new String[]{
                "/Users/huanggang/gitroot/data/hadoop-learning/data/FLOW.log",
                "/Users/huanggang/Downloads/flow_out"
        };

        Configuration conf = new Configuration();
        // 获取Job对象
        Job job = Job.getInstance(conf);

        // 设置Jar
        job.setJarByClass(FlowDriver.class);

        // 关联mapper 和 reducer
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        // 设置mapper的key 和value
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        // 设置reducer 的key 和value
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        // 设置输出路径
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 结果写成几个文件 有几个Reduce
        job.setNumReduceTasks(2);

        // 提交Job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);

    }
}
