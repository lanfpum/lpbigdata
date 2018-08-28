package main.java.lxpsee.top.mr.localwc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/15 12:25.
 * <p>
 * 字符统计，本机上
 * <p>
 *  注释  configuration.set("fs.defaultFS", "file:///");
 * hadoop jar Hdfs.jar lxpsee.top.mr.localwc.WCApp hdfs://ip201/user/lanp/mr/mr06 hdfs://ip201/user/lanp/mr/mr06/out
 *
 * 02,注释分区代码
 *  在文件系统中添加多个文件
 *  更改mapper和reducer中计数器代码
 */
public class WCApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
//        configuration.set("fs.defaultFS", "file:///");

        Job job = Job.getInstance(configuration);

        job.setJobName("new WCApp");
        job.setJarByClass(WCApp.class);
        job.setInputFormatClass(TextInputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

//        job.setPartitionerClass(MyPartitioner.class);
        job.setCombinerClass(WCReducer.class);

        job.setMapperClass(WCMapper.class);
        job.setReducerClass(WCReducer.class);

        job.setNumReduceTasks(3);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.waitForCompletion(true);
    }

}
