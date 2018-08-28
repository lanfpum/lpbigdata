package main.java.lxpsee.top.mr.multiinput;

import lxpsee.top.mr.localwc.WCReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/15 16:47.
 * <p>
 * 两个注意点：
 * 1.使用 MultipleInputs添加多个输入
 * 2.每个输入应该对应不同的mapper，因为其对应的输入不同
 * 3.读取的文件 应该在不同的目录下
 */
public class WCApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "file:///");

        Job job = Job.getInstance(configuration);

        job.setJobName("multiinput WCApp");
        job.setJarByClass(WCApp.class);

        MultipleInputs.addInputPath(job, new Path("D:\\workDir\\com\\multi\\txt"), TextInputFormat.class, WCTextMapper.class);
        MultipleInputs.addInputPath(job, new Path("D:\\workDir\\com\\multi\\seq"), SequenceFileInputFormat.class, WCSeqMapper.class);

        FileOutputFormat.setOutputPath(job, new Path(args[0]));

        job.setReducerClass(WCReducer.class);

        job.setNumReduceTasks(3);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.waitForCompletion(true);
    }

}
