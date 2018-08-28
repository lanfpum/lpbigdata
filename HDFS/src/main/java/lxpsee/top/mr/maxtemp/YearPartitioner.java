package main.java.lxpsee.top.mr.maxtemp;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/16 11:20.
 * <p>
 * 按年份分区,简单的根据年份划分，不符合实际要求
 */
public class YearPartitioner extends Partitioner<IntWritable, IntWritable> {

    @Override
    public int getPartition(IntWritable year, IntWritable temp, int part) {
        int y = year.get() - 1970;

        if (y < 10) {
            return 0;
        } else if (y >= 10 && y < 20) {
            return 1;
        } else {
            return 2;
        }
    }
}
