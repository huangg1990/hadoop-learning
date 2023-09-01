package com.huangg.hdfs;

import com.huangg.hdfs.config.HdfsClientConfig;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 *
 */
public class _02HdfsAPI {
    public static void main(String[] args) throws Exception {
        FileSystem fs= HdfsClientConfig.getHdfsClient();

        Path localFilePath= new Path("/Users/huanggang/Downloads/xx.txt");
        Path hdfsPath= new Path("/huangg/java/xx.txt");
        fs.copyFromLocalFile(localFilePath,hdfsPath);

        System.out.println("copyFromLocalFile done");

    }
}
