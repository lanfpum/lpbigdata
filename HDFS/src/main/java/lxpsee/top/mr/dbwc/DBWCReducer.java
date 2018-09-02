package lxpsee.top.mr.dbwc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/20 16:57.
 */
/*
// 写入本地的reduce
public class DBWCReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;

        for (IntWritable value : values) {
            count += value.get();
        }

        context.write(key, new IntWritable(count));
    }
}
*/

/**
 * 写入数据库的
 */
public class DBWCReducer extends Reducer<Text, IntWritable, MyDBWritable, NullWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;

        for (IntWritable value : values) {
            count += value.get();
        }

        MyDBWritable keyOut = new MyDBWritable();
        keyOut.setWord(key.toString());
        keyOut.setWordCount(count);
        context.write(keyOut, NullWritable.get());
    }
}


