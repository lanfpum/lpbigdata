package lxpsee.top.mr.dbwc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/20 16:51.
 */
public class DBWCMapper extends Mapper<LongWritable, MyDBWritable, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, MyDBWritable value, Context context) throws IOException, InterruptedException {
        System.out.println(key + "------" + value.getId() + ":" + value.getName() + ":" + value.getTxt());
        String[] arr = value.getTxt().split(" ");

        for (String s : arr) {
            context.write(new Text(s), new IntWritable(1));
        }
    }
}
