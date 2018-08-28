package main.java.lxpsee.top.mr.secondarysort;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/19 17:00.
 * <p>
 * 注意：文本输入默认是LongWritable,获得NullWritable是NullWritable.get()
 * 读取每一行，按空格切分成数组，构建自定义key
 * bug:在APP中输出类型设置错误。默认是和Reduce一样
 */
public class SecondarySortMapper extends Mapper<LongWritable, Text, ComboKey, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] arr = value.toString().split(" ");
        ComboKey comboKey = new ComboKey(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
        context.write(comboKey, NullWritable.get());

       /* String line = value.toString();
        String[] arr = line.split(" ");

        ComboKey keyOut = new ComboKey();
        keyOut.setYear(Integer.parseInt(arr[0]));
        keyOut.setTemp(Integer.parseInt(arr[1]));
        context.write(keyOut, NullWritable.get());*/
    }
}
