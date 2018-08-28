package main.java.lxpsee.top.mr.mapjoin.reducejoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/27 09:12.
 */
public class ReduceJoinMapper extends Mapper<LongWritable, Text, ComboKey4ReduceJoin, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String path = fileSplit.getPath().toString();
        ComboKey4ReduceJoin comboKey4ReduceJoin = new ComboKey4ReduceJoin();

        if (path.contains("customers")) {
            int cid = Integer.parseInt(line.substring(0, line.indexOf(",")));
            String customerInfo = line;
            comboKey4ReduceJoin.setType(0);
            comboKey4ReduceJoin.setCid(cid);
            comboKey4ReduceJoin.setCustomerINfo(customerInfo);
        } else {
            int cid = Integer.parseInt(line.substring(line.lastIndexOf(",") + 1));
            String oid = line.substring(0, line.indexOf(","));
            String orderInfo = line.substring(0, line.lastIndexOf(","));
            comboKey4ReduceJoin.setType(1);
            comboKey4ReduceJoin.setCid(cid);
            comboKey4ReduceJoin.setOid(Integer.parseInt(oid));
            comboKey4ReduceJoin.setOrderInfo(orderInfo);
        }

        context.write(comboKey4ReduceJoin, NullWritable.get());
    }
}
