package com.huangg.mr.flowStat;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
    private Text k = new Text();
    private FlowBean v = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, FlowBean>.Context context) throws IOException, InterruptedException {

        // 1	112.19.11.10	1116	65	200
        // 1 获取一行
        String line = value.toString();
        // 2 切割 \t
        String[] arr = line.split(",");
        // 3 封装对象
        Long downFlow = Long.parseLong(arr[2]);
        Long upFlow = Long.parseLong(arr[3]);

        v.updValue(upFlow, downFlow);

        // 4写出
        k.set(arr[0]);

        context.write(k, v);

    }
}
