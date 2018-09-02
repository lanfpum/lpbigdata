package lxpsee.top.mr.mapjoin.reducejoin;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/27 10:38.
 */
public class CIDGroupComparator extends WritableComparator {
    public CIDGroupComparator() {
        super(ComboKey4ReduceJoin.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        ComboKey4ReduceJoin key1 = (ComboKey4ReduceJoin) a;
        ComboKey4ReduceJoin key2 = (ComboKey4ReduceJoin) b;
        return key1.getCid() - key2.getCid();
    }
}
