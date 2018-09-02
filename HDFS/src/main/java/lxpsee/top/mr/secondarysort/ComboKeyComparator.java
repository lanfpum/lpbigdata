package lxpsee.top.mr.secondarysort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/19 16:55.
 * <p>
 * 自定义key排序对比器：空参构造，调用父类的有参构造，重写compare（），按key比较
 */
public class ComboKeyComparator extends WritableComparator {
    protected ComboKeyComparator() {
        super(ComboKey.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        ComboKey comboKey1 = (ComboKey) a;
        ComboKey comboKey2 = (ComboKey) b;
        return comboKey1.compareTo(comboKey2);
    }
}
