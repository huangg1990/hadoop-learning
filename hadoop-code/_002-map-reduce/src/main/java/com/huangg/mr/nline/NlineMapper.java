package com.huangg.mr.nline;

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
public class NlineMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private Text k = new Text();
    private final static IntWritable v = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
            throws IOException, InterruptedException {

        // 个文件中的起始字节偏移量
        System.out.println(key.toString());

        // 1 获取一行数据
        String line = value.toString();
        // 2 切分单词
        String[] words = line.split(" ");
        // 3 循环写入
        for (String word : words) {
            k.set(word);
            context.write(k, v);
        }

    }
}
