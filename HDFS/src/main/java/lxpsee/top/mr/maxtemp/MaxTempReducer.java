package main.java.lxpsee.top.mr.maxtemp;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/15 12:16.
 * <p>
 * 应注意使用int max 接收，且写出应该在循环后面
 */
public class MaxTempReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {

    @Override
    protected void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int max = Integer.MIN_VALUE;

        for (IntWritable value : values) {
            max = (max > value.get()) ? max : value.get();
        }

        context.write(key, new IntWritable(max));
    }
}
