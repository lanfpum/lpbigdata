package lxpsee.top.mr.multiinput;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/15 12:11.
 */
public class WCSeqMapper extends Mapper<IntWritable, Text, Text, IntWritable> {

    @Override
    protected void map(IntWritable key, Text value, Context context) throws IOException, InterruptedException {
        Text keyOut = new Text();
        IntWritable valueOut = new IntWritable();
        String[] arr = value.toString().split(" ");

        for (String s : arr) {
            keyOut.set(s);
            valueOut.set(1);
            context.write(keyOut, valueOut);
        }
    }
}
