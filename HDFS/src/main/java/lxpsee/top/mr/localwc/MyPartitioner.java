package main.java.lxpsee.top.mr.localwc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;


/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/15 12:22.
 * <p>
 * 自定义分区
 */
public class MyPartitioner extends Partitioner<Text, IntWritable> {

    public MyPartitioner() {
        System.out.println("LP MyPartitioner()");
    }

    @Override
    public int getPartition(Text text, IntWritable intWritable, int numPartitions) {
        System.out.println(" LP MyPartitioner ........");
        return 0;
    }
}
