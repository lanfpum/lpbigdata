package main.java.lxpsee.top.mr.secondarysort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/17 17:21.
 * <p>
 * 年份和温度的组合key,两者比较是，与被比较者前负后正
 * 注意readFields（），直接使用属性，不能在前面有int，会全部赋值0
 */
public class ComboKey implements WritableComparable<ComboKey> {
    private int year;
    private int temp;

    public ComboKey() {
    }

    public ComboKey(int year, int temp) {
        this.year = year;
        this.temp = temp;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int compareTo(ComboKey o) {
        int y = o.getYear();
        int t = o.getTemp();

        // 年份升序，，气温降序
        if (year == y) {
            return -(temp - t);
        } else {
            return year - y;
        }
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(year);
        out.writeInt(temp);
    }

    public void readFields(DataInput in) throws IOException {
        year = in.readInt();
        temp = in.readInt();
    }
}
