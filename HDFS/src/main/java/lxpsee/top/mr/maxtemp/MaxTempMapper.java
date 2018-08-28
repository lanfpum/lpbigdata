package main.java.lxpsee.top.mr.maxtemp;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/15 12:11.
 */
public class MaxTempMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] arr = value.toString().split(" ");
        context.write(new IntWritable(Integer.valueOf(arr[0])), new IntWritable(Integer.valueOf(arr[1])));
    }
}
