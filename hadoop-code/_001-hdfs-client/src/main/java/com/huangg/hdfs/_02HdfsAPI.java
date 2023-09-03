package com.huangg.hdfs;

import com.huangg.hdfs.config.HdfsClientConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.net.URI;

/**
 * 参数优先级
 * 1） 客户端代码设置的值
 * 2） ClassPath下的用户自定义配置文件
 * 3） 然后是服务器的默认配置
 */
public class _02HdfsAPI {
    public static void main(String[] args) throws Exception {
//        FileSystem fs= HdfsClientConfig.getHdfsClient();

//        copyFromLocalFile();

//        copyToLocalFile();

//        deleteFile();

//        rename();

//        listFiles();

        isFile();

        System.out.println("===== successful =====");
    }

    /**
     * 文件上传
     */
    private static void copyFromLocalFile() throws Exception {
        Configuration conf = new Configuration();
        conf.set("dfs.replication", "2");
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop221:9000"), conf, "hadoop");


        Path localFilePath = new Path("/Users/huanggang/Downloads/xx.txt");
        Path hdfsPath = new Path("/huangg/java/xx.txt");
        fs.copyFromLocalFile(localFilePath, hdfsPath);
        fs.close();
        System.out.println("copyFromLocalFile done");
    }

    /**
     * 文件下载
     *
     * @throws Exception
     */
    private static void copyToLocalFile() throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop221:9000"), conf, "hadoop");

//        fs.copyToLocalFile(
//                new Path("/huangg/java/xx.txt")
//                , new Path("/Users/huanggang/Downloads/hdfs2local.txt")
//        );


        fs.copyToLocalFile(
                false
                , new Path("/huangg/java/xx.txt")
                , new Path("/Users/huanggang/Downloads/hdfs2local.txt")
                , true
        );

        fs.close();


        System.out.println("copyToLocalFile done");

    }

    /**
     * 删除文件
     *
     * @throws Exception
     */
    private static void deleteFile() throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop221:9000"), conf, "hadoop");

        fs.delete(
                new Path("/huangg/java/xx.txt")
                , true // 删除目录 时使用递归
        );


        fs.close();
        System.out.println("delete successful");
    }


    /**
     * 文件重命令
     * @throws Exception
     */
    private static void rename() throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop221:9000"), conf, "hadoop");

        fs.rename(new Path("/huangg/java2"), new Path("/huangg/java2New"));

        fs.close();

    }

    /**
     * 列出文件信息
     * @throws Exception
     */
    private static void listFiles() throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop221:9000"), conf, "hadoop");


        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path(""), true);
        while(listFiles.hasNext()){
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println(fileStatus.getPath().getName()); //文件名称
            System.out.println(fileStatus.getPermission()); // 文件权限
            System.out.println(fileStatus.getLen()); // 文件大小

            for(BlockLocation blockLocation: fileStatus.getBlockLocations()){
                String[] hosts = blockLocation.getHosts();
                for(String host:hosts){
                    System.out.println(host);
                }
            }
            System.out.println("=========================================");
        }

        fs.close();

    }

    /**
     *  判断目录还是文件
     * @throws Exception
     */
    private static void isFile() throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop221:9000"), conf, "hadoop");

        FileStatus[]  listFiles = fs.listStatus(new Path("/"));
        for(FileStatus fileStatus:listFiles){
            if(fileStatus.isFile()){
                System.out.println(" is file");
            }else{
                System.out.println(" is dir ");
            }
        }

        fs.close();
    }
}
