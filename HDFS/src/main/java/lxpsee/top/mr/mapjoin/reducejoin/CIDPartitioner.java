package lxpsee.top.mr.mapjoin.reducejoin;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/27 10:26.
 */
public class CIDPartitioner extends Partitioner<ComboKey4ReduceJoin, NullWritable> {

    public int getPartition(ComboKey4ReduceJoin key, NullWritable nullWritable, int numPartitions) {
        return key.getCid() % numPartitions;
    }
}
