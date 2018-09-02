package lxpsee.top.mr.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/8 11:35.
 */
public class WCApp {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
//        configuration.set("fs.defaultFS", "file:///");

        Job job = Job.getInstance(configuration);

        job.setJobName("WCApp");
        job.setJarByClass(WCApp.class);
        job.setInputFormatClass(TextInputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(WCMapper.class);
        job.setReducerClass(WCReducer.class);

        job.setNumReduceTasks(1);

//        job.setMapOutputKeyClass(Text.class);
//        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.waitForCompletion(false);
//        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
