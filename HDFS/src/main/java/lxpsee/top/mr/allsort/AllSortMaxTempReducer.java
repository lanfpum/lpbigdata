package lxpsee.top.mr.allsort;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/16 15:50.
 */
public class AllSortMaxTempReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
    @Override
    protected void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int max = Integer.MIN_VALUE;

        for (IntWritable value : values) {
            max = max > value.get() ? max : value.get();
        }

        context.write(key, new IntWritable(max));
    }
}
