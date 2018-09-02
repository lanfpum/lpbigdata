package lxpsee.top.mr.secondarysort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/19 16:29.
 * <p>
 * 自定义分区类 : 按年份分区，年份求余分区数
 */
public class YearPartitioner extends Partitioner<ComboKey, NullWritable> {
    @Override
    public int getPartition(ComboKey key, NullWritable nullWritable, int numPartitions) {
        int year = key.getYear();
        return year % numPartitions;
    }
}
