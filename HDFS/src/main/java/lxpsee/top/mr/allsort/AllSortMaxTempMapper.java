package lxpsee.top.mr.allsort;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/16 15:48.
 */
public class AllSortMaxTempMapper extends Mapper<IntWritable, IntWritable, IntWritable, IntWritable> {
    @Override
    protected void map(IntWritable key, IntWritable value, Context context) throws IOException, InterruptedException {
        context.write(key, value);
    }
}
