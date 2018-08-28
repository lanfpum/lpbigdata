package main.java.lxpsee.top.mr.secondarysort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/19 16:33.
 * <p>
 * 自定义分组对比器：空参构造，调用父类的有参构造，重写compare（），按年份分组
 */
public class YearGroupComparator extends WritableComparator {
    protected YearGroupComparator() {
        super(ComboKey.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        ComboKey comboKey1 = (ComboKey) a;
        ComboKey comboKey2 = (ComboKey) b;
        return comboKey1.getYear() - comboKey2.getYear();
    }
}
