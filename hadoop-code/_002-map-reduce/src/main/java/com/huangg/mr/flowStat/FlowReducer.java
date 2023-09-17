package com.huangg.mr.flowStat;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

    private FlowBean v = new FlowBean();

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context)
            throws IOException, InterruptedException {

        long downSumFlow = 0L;
        long upSumFlow = 0L;

        // 1 累加求全
        for (FlowBean v : values) {
            downSumFlow += v.getTotalDownFlow();
            upSumFlow += v.getTotalUpFlow();
        }

        v.updValue(upSumFlow, downSumFlow);

        // 2 写出
        context.write(key, v);

    }
}
