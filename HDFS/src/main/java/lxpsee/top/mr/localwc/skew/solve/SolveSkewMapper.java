package main.java.lxpsee.top.mr.localwc.skew.solve;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/20 11:01.
 */
/*public class SolveSkewMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
      *//*  String[] arr = value.toString().split("\t");
        context.write(new Text(arr[0]), new IntWritable(Integer.parseInt(arr[1])));*//*
    }
}*/

public class SolveSkewMapper extends Mapper<Text, Text, Text, IntWritable> {
    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        context.write(key, new IntWritable(Integer.parseInt(value.toString())));
    }
}