package com.huangg.mr.kv;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Map 阶段
 * keyin 输入数据的KEY
 * Value 输入数字的valeu
 * keyout 输出数据的Key的类型
 * valueout 输出的数据value类型
 */
public class KVMapper extends Mapper<Text, Text, Text, IntWritable> {

    private final static IntWritable v = new IntWritable(1);

    @Override
    protected void map(Text key, Text value, Mapper<Text, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        context.write(key,v);
    }
}
