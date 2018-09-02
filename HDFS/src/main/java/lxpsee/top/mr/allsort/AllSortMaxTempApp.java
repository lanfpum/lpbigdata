package lxpsee.top.mr.allsort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.lib.TotalOrderPartitioner;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/16 10:46.
 * <p>
 * 年份最大温度统计
 */
public class AllSortMaxTempApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "file:///");

        Job job = Job.getInstance(configuration);

        job.setJobName("AllSortMaxTempApp allsort");
        job.setJarByClass(AllSortMaxTempApp.class);
        job.setInputFormatClass(SequenceFileInputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(AllSortMaxTempMapper.class);
        job.setReducerClass(AllSortMaxTempReducer.class);

        job.setNumReduceTasks(5);

        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);

        job.setPartitionerClass(TotalOrderPartitioner.class);
        TotalOrderPartitioner.setPartitionFile(job.getConfiguration(), new Path("file:///D:/workDir/maxtemp/seq/par.lst"));
        InputSampler.RandomSampler<IntWritable, IntWritable> sampler = new InputSampler.RandomSampler<IntWritable, IntWritable>(0.5, 5000, 4);
        InputSampler.writePartitionFile(job, sampler);

        job.waitForCompletion(true);
    }
}
