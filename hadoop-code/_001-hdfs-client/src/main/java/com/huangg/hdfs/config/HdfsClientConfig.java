package com.huangg.hdfs.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.net.URI;

public class HdfsClientConfig {

    private static FileSystem fs = null;


    public static FileSystem getHdfsClient() {
        if(fs==null){
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", "hdfs://hadoop221:9000");
            try {
                fs = FileSystem.get(new URI("hdfs://hadoop221:9000"), conf, "hadoop");
            } catch (Exception e) {
                throw new RuntimeException("HdfsClientConfig init error", e);
            }
        }
        return fs;
    }
}
