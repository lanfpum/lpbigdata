package main.java.lxpsee.top.mr.localwc.skew.solve;

import lxpsee.top.mr.localwc.WCReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/20 10:59.
 * <p>
 * 对随机分区进行再次运算解决数据倾斜
 * <p>
 * 使用hadoop的KeyValueTextInputFormat解决数据倾斜
 */
public class SolveSkewWCApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "file:///");

        Job job = Job.getInstance(configuration);

        job.setJobName("Solve SKEW WCApp");
        job.setJarByClass(SolveSkewWCApp.class);
//        job.setInputFormatClass(TextInputFormat.class);
        job.setInputFormatClass(KeyValueTextInputFormat.class);

        FileInputFormat.addInputPath(job, new Path("D:\\workDir\\wc\\out\\part-r-00000"));
        FileInputFormat.addInputPath(job, new Path("D:\\workDir\\wc\\out\\part-r-00001"));
        FileInputFormat.addInputPath(job, new Path("D:\\workDir\\wc\\out\\part-r-00002"));
        FileInputFormat.addInputPath(job, new Path("D:\\workDir\\wc\\out\\part-r-00003"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\workDir\\wc\\out2"));

        job.setMapperClass(SolveSkewMapper.class);
        job.setReducerClass(WCReducer.class);

        job.setNumReduceTasks(4);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.waitForCompletion(true);
    }
}
