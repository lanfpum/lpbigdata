package lxpsee.top.mr.localwc.skew;

import lxpsee.top.mr.localwc.WCMapper;
import lxpsee.top.mr.localwc.WCReducer;
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
 * Created by 努力常态化 on 2018/8/20 10:49.
 * <p>
 * 数据倾斜
 * <p>
 * 添加自定义分区函数可以解决数据倾斜
 */
public class SkewWCApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "file:///");

        Job job = Job.getInstance(configuration);

        job.setJobName("SKEW WCApp");
        job.setJarByClass(SkewWCApp.class);
        job.setInputFormatClass(TextInputFormat.class);

        FileInputFormat.addInputPath(job, new Path("D:\\workDir\\wc"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\workDir\\wc\\out"));

        job.setPartitionerClass(SkewPartitioner.class);

        job.setMapperClass(WCMapper.class);
        job.setReducerClass(WCReducer.class);

        job.setNumReduceTasks(4);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.waitForCompletion(true);
    }
}
