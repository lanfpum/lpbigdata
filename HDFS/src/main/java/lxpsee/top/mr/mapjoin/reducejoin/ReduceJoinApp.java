package main.java.lxpsee.top.mr.mapjoin.reducejoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/27 10:53.
 */
public class ReduceJoinApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "file:///");

        Job job = Job.getInstance(configuration);
        job.setJobName("ReduceJoinApp");
        job.setJarByClass(ReduceJoinApp.class);

        FileInputFormat.addInputPath(job, new Path("D:\\workDir\\join"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\workDir\\join\\out2"));

        job.setMapperClass(ReduceJoinMapper.class);
        job.setReducerClass(ReduceJoinReducer.class);

        job.setMapOutputKeyClass(ComboKey4ReduceJoin.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        job.setPartitionerClass(CIDPartitioner.class);
        job.setGroupingComparatorClass(CIDGroupComparator.class);
        job.setSortComparatorClass(ComboKey4ReduceJoinComparator.class);
        job.setNumReduceTasks(2);

        job.waitForCompletion(true);
    }
}
