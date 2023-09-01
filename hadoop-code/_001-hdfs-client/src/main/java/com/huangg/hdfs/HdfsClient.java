package com.huangg.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;


/**
 * HDFS 操作demo
 *
 */
public class HdfsClient {
    public static void main(String[] args) throws Exception {
        // 这个很重要 不然没有权限
//        System.setProperty("HADOOP_USER_NAME","hadoop");

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://hadoop221:9000");

        // 1 获取HDFS客户端对象
//        FileSystem fs= FileSystem.get(conf);
        // 1 直接指定用户方式
        FileSystem fs= FileSystem.get(new URI("hdfs://hadoop221:9000"),conf,"hadoop");
                // 2 创建目录

        fs.mkdirs(new Path("/huangg/java2"));

        // 3 关闭客户端
        fs.close();

        System.out.println("=================== done ================");

    }
}
