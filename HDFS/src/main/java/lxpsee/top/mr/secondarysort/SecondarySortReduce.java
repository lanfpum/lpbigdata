package lxpsee.top.mr.secondarysort;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/19 17:07.
 */
public class SecondarySortReduce extends Reducer<ComboKey, NullWritable, IntWritable, IntWritable> {

    @Override
    protected void reduce(ComboKey key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        int year = key.getYear();
        int temp = key.getTemp();

        System.out.println("----------------------- reduce() -------------------");

        for (NullWritable value : values) {
            System.out.println(key.getYear() + " -- " + key.getTemp());
        }

        context.write(new IntWritable(year), new IntWritable(temp));
    }
}
