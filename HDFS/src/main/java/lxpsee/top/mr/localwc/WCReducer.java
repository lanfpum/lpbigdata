package main.java.lxpsee.top.mr.localwc;

import lxpsee.top.mr.utils.Util;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/15 12:16.
 */

public class WCReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    public WCReducer() {
        System.out.println("LP AllSortMaxTempReducer()");
    }

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;

        for (IntWritable value : values) {
            count += value.get();
        }

        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " ; AllSortMaxTempReducer :" + key.toString() + " --- " + count);
        context.write(key, new IntWritable(count));
        context.getCounter("r", Util.getInfo(this, "reduce()")).increment(1);
    }
}
