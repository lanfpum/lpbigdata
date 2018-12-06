package lxpsee.top.userdrawmr;

import lxpsee.top.userdrawmr.UserDrawMapReduce2.MyMap2;
import lxpsee.top.userdrawmr.UserDrawMapReduce2.MyReduce2;
import lxpsee.top.userdrawputinhbase.UserDrawPutInHbaseMap;
import lxpsee.top.userdrawputinhbase.UserDrawPutInHbaseReduce;
import lxpsee.top.utils.Config;
import lxpsee.top.utils.TextArrayWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/5 19:53.
 */
public class UserDrawMapReduce {
    public static Config config = new Config();

    public static class MyMap extends Mapper<LongWritable, Text, Text, TextArrayWritable> {
        Text text = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String line = value.toString();
            String[] dataArray = line.split(config.Separator);
            // MDN + appId
            String uiqKey = dataArray[Integer.parseInt(config.MDN)]
                    + dataArray[Integer.parseInt(config.appID)];
            String[] val = new String[5];
            String timeNow = dataArray[Integer.parseInt(config.Date)];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

            // 时间，手机号，APPid，计数，使用时长
            val[0] = simpleDateFormat.format(Long.parseLong(timeNow));
            val[1] = dataArray[Integer.parseInt(config.MDN)];
            val[2] = dataArray[Integer.parseInt(config.appID)];
            val[3] = "1";
            val[4] = dataArray[Integer.parseInt(config.ProcedureTime)];

            text.set(uiqKey);
            context.write(text, new TextArrayWritable(val));
        }
    }

    public static class MyReduce extends Reducer<Text, TextArrayWritable, Text, Text> {
        Text text = new Text();

        @Override
        protected void reduce(Text key, Iterable<TextArrayWritable> values, Context context)
                throws IOException, InterruptedException {
            long sum = 0;
            int count = 0;
            String[] res = new String[5];
            boolean flag = true;

            for (TextArrayWritable textArrayWritable : values) {
                String[] vals = textArrayWritable.toStrings();

                if (flag) {
                    res = vals;
                }

                if (vals[3] != null) {
                    count += 1;
                }

                if (vals[4] != null) {
                    sum += Long.valueOf(vals[4]);
                }
            }

            res[3] = String.valueOf(count);
            res[4] = String.valueOf(sum);

            // 时间，手机号，appId，计数，使用时长
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(res[0]).append("|");
            stringBuffer.append(res[1]).append("|");
            stringBuffer.append(res[2]).append("|");
            stringBuffer.append(res[3]).append("|");
            stringBuffer.append(res[4]);

            text.set(stringBuffer.toString());
            context.write(null, text);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        Job job1 = Job.getInstance(configuration, "UserDrawMapReduceJob1");
        job1.setJarByClass(UserDrawMapReduce.class);

        job1.setMapperClass(MyMap.class);
        job1.setReducerClass(MyReduce.class);

        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(TextArrayWritable.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(Text.class);

        job1.setInputFormatClass(TextInputFormat.class);
        job1.setOutputFormatClass(TextOutputFormat.class);

        // 输入路径， 输出路径
       /* FileInputFormat.addInputPath(job1, new Path(args[0]));
        FileOutputFormat.setOutputPath(job1, new Path(args[1]));*/
        FileInputFormat.addInputPath(job1, new Path("file:///D:/workDir/otherFile/scala/userdraw/data"));
        FileOutputFormat.setOutputPath(job1, new Path("file:///D:/workDir/otherFile/scala/userdraw/data/out"));

        Boolean state1 = job1.waitForCompletion(true);
        System.out.println("job1执行成功！！");

        if (state1) {
            configuration = new Configuration();
            Job job2 = Job.getInstance(configuration, "UserDrawMapReduceJob2");
            job2.setJarByClass(UserDrawMapReduce.class);

            job2.setMapperClass(MyMap2.class);
            job2.setReducerClass(MyReduce2.class);

            job2.setMapOutputKeyClass(Text.class);
            job2.setMapOutputValueClass(Text.class);
            job2.setOutputKeyClass(Text.class);
            job2.setOutputValueClass(Text.class);

            job2.setInputFormatClass(TextInputFormat.class);
            job2.setOutputFormatClass(TextOutputFormat.class);

            /*FileInputFormat.addInputPath(job2, new Path(args[1]));// 输入路径
            FileOutputFormat.setOutputPath(job2, new Path(args[2]));// 输出路径*/
            FileInputFormat.addInputPath(job2, new Path("file:///D:/workDir/otherFile/scala/userdraw/data/out"));// 输入路径
            FileOutputFormat.setOutputPath(job2, new Path("file:///D:/workDir/otherFile/scala/userdraw/data/out2"));// 输出路径

            Boolean state2 = job2.waitForCompletion(true);
            System.out.println("job2执行成功！！！");

            if (state2) {
                configuration = new Configuration();
                // 设置zookeeper
                configuration.set(config.consite, config.hbaseip);
                // 设置hbase表名称
                configuration.set(TableOutputFormat.OUTPUT_TABLE, config.tableDraw);
                // 将该值改大，防止hbase超时退出
                configuration.set(config.coftime, config.time);
                Job job3 = Job.getInstance(configuration, "UserDrawPutInHbase");
                job3.setJarByClass(UserDrawMapReduce.class);
                TableMapReduceUtil.addDependencyJars(job3);

                FileInputFormat.setInputPaths(job3, new Path(args[2]));

                job3.setMapperClass(UserDrawPutInHbaseMap.class);
                job3.setMapOutputKeyClass(Text.class);
                job3.setMapOutputValueClass(Text.class);

                job3.setReducerClass(UserDrawPutInHbaseReduce.class);
                job3.setOutputFormatClass(TableOutputFormat.class);

                job3.waitForCompletion(true);
            }
        }

    }

}
