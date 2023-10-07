package com.huangg.mr.flowStat;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 自定义 分区
 *
 */
public class ProvincePartitioner  extends Partitioner<Text,FlowBean> {
    @Override
    public int getPartition(Text text, FlowBean flowBean, int numPartitions) {
        String k= text.toString();
        int partition=4;
        if (k.startsWith("136")) {
            partition=0;
        }else if(k.startsWith("137")){
            partition=1;
        }else if(k.startsWith("138")) {
            partition=2;
        }else if(k.startsWith("139")) {
            partition=3;
        }

        return partition;
    }
}
