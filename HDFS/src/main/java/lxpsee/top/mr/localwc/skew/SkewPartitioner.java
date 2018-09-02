package lxpsee.top.mr.localwc.skew;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.Random;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/20 10:55.
 * <p>
 * 自定义分区函数：随机分区
 */
public class SkewPartitioner extends Partitioner<Text, IntWritable> {
    @Override
    public int getPartition(Text text, IntWritable intWritable, int numPartitions) {
        return new Random().nextInt(numPartitions);
    }
}
