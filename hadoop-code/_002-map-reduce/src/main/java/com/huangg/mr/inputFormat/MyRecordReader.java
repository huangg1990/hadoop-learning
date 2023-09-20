package com.huangg.mr.inputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * 一个切片调用一次
 */
public class MyRecordReader extends RecordReader<Text, BytesWritable> {

    FileSplit split;
    Configuration conf;

    Text k = new Text();
    BytesWritable v = new BytesWritable();
    boolean isProgress = true;

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        //初始化
        this.split = (FileSplit) split;
        this.conf = context.getConfiguration();

    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        // 核心业务逻辑
        if (isProgress) {
            byte[] buf = new byte[(int) split.getLength()];

            // 1 获取FS对象
            Path path = split.getPath();
            FileSystem fs = path.getFileSystem(conf);

            // 2 获取输流
            FSDataInputStream fis = fs.open(path);

            // 3 拷贝
            IOUtils.readFully(fis, buf, 0, buf.length);

            // 4 封装V
            v.set(buf, 0, buf.length);

            // 5 设置K
            k.set(path.toString());

            // 6 关闭资源
            IOUtils.closeStream(fis);

            isProgress = false;

            return true;
        }

        return false;
    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return k;
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return v;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        // 进度条
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}
