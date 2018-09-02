package lxpsee.top.mr.mapjoin.reducejoin;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/27 10:28.
 */
public class ReduceJoinReducer extends Reducer<ComboKey4ReduceJoin, NullWritable, Text, NullWritable> {
    @Override
    protected void reduce(ComboKey4ReduceJoin key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        Iterator<NullWritable> iterator = values.iterator();
        iterator.next();
        String customerINfo = key.getCustomerINfo();

        while (iterator.hasNext()) {
            iterator.next();
            String orderInfo = key.getOrderInfo();
            Text text = new Text(customerINfo + "," + orderInfo);
            context.write(text, NullWritable.get());
        }

    }
}


