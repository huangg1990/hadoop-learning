package com.huangg.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;

/**
 *  低级API
 *
 */
public class _03HDFS_IO {
    public static void main(String[] args) throws Exception {
//        upload();

        download();

        System.out.println("successful");
    }

    /**
     * 文件上传
     */
    private static void upload() throws Exception {
        // 1 获取对象
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop221:9000"), conf, "hadoop");

        // 2 获取输入流
        FileInputStream fis = new FileInputStream(new File("/Users/huanggang/Downloads/xx.txt"));

        // 3 获取输出流
        FSDataOutputStream fos = fs.create(new Path("/huangg.txt"));
        // 4 流的对拷
        IOUtils.copyBytes(fis, fos, conf);

        // 5 关闭资源
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
        fs.close();
    }

    /**
     * 文件下载
     * @throws Exception
     */
    private static void download() throws Exception {
        // 1 获取对象
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop221:9000"), conf, "hadoop");

        // 2 获取输入流
        FSDataInputStream fis = fs.open(new Path("/huangg.txt"));

        // 3 获取输出流
        FileOutputStream fos = new FileOutputStream(new File("/Users/huanggang/Downloads/io_download.txt"));
        // 4 流的对拷
        IOUtils.copyBytes(fis, fos, conf);

        // 5 关闭资源
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
        fs.close();
    }

    /**
     * 定位读取
     */
    public static void readPart1()throws Exception{
        // 1 获取对象
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop221:9000"), conf, "hadoop");

        // 2 获取输入流
         FSDataInputStream fis = fs.open(new Path("/hadoop.gz"));

        // 3 获取输出流
        FileOutputStream fos = new FileOutputStream(new File( "/Users/huanggang/Downloads/hadoop.gz.part1"));
        // 4 流的拷贝 只要 128MB 一块的大小
        byte[] buf =new byte[1024];
        for(int i=0;i<1024*128;i++){
            fis.read(buf);
            fos.write(buf);
        }

        // 5 关闭资源
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
        fs.close();
    }
    /**
     * 定位读取
     */
    public static void readPart2()throws Exception{
        // 1 获取对象
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop221:9000"), conf, "hadoop");

        // 2 获取输入流
        FSDataInputStream fis = fs.open(new Path("/hadoop.gz"));

        // 2 指定读取的起点 从128以后开始读
        fis.seek(1024*1024*128);

        // 3 获取输出流
        FileOutputStream fos = new FileOutputStream(new File( "/Users/huanggang/Downloads/hadoop.gz.part2"));

        // 5 关闭资源
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
        fs.close();
    }
}
// cat part2 >> part1 hadoop.gz
// type part2 >> part1 hadoop.gz  // window系统

