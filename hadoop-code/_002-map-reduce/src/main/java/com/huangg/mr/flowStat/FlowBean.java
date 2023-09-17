package com.huangg.mr.flowStat;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 自定义 流量统计Bean
 */
public class FlowBean implements Writable {

    private long totalUpFlow;
    private long totalDownFlow;
    private long totalFlow;

    public long getTotalUpFlow() {
        return totalUpFlow;
    }

    public void setTotalUpFlow(long totalUpFlow) {
        this.totalUpFlow = totalUpFlow;
    }

    public long getTotalDownFlow() {
        return totalDownFlow;
    }

    public void setTotalDownFlow(long totalDownFlow) {
        this.totalDownFlow = totalDownFlow;
    }

    public long getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(long totalFlow) {
        this.totalFlow = totalFlow;
    }

    public FlowBean(){}

    public void updValue(long totalUpFlow, long totalDownFlow) {
        this.totalUpFlow = totalUpFlow;
        this.totalDownFlow = totalDownFlow;
        this.totalFlow = totalUpFlow + totalDownFlow;
    }

    /**
     * 序列化
     * @param out
     * @throws IOException
     */
    @Override
    public void write(DataOutput out) throws IOException {
            out.writeLong(totalUpFlow);
            out.writeLong(totalDownFlow);
            out.writeLong(totalFlow);
    }

    /**
     * 反序列化
     * @param in
     * @throws IOException
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        totalUpFlow = in.readLong();
        totalDownFlow = in.readLong();
        totalFlow = in .readLong();
    }

    @Override
    public String toString() {
        return totalUpFlow+"\t"+ totalDownFlow +"\t"+ totalFlow;
    }
}
