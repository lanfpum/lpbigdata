package main.java.lxpsee.top.mr.dbwc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hadoop.mapred.lib.db.DBInputFormat;
import org.apache.hadoop.mapred.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/20 16:59.
 */
public class DBWCApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
//        configuration.set("fs.defaultFS", "file:///");

        Job job = Job.getInstance(configuration);

        job.setJobName("DBWCAPP");
        job.setJarByClass(DBWCApp.class);

        String driverclass = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/big4";
        String username = "root";
        String password = "123";

        DBConfiguration.configureDB(job.getConfiguration(), driverclass, url, username, password);
        DBInputFormat.setInput(job, MyDBWritable.class, "select id,name,txt from words", "select count(*) from words");

//        FileOutputFormat.setOutputPath(job, new Path("D:\\workDir\\wc\\db\\out"));
        DBOutputFormat.setOutput(job, "stats", "word", "count");

        job.setMapperClass(DBWCMapper.class);
        job.setReducerClass(DBWCReducer.class);

        job.setNumReduceTasks(3);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.waitForCompletion(true);
    }
}
