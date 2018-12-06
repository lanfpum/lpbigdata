package lxpsee.top.test;

import lxpsee.top.utils.RowKeyUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/8 12:29.
 * <p>
 * rowKey的设计测试
 */
public class TestRowKey {
    @Test
    public void testPut() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:calllogs"));

        String callerId = "18011112222";
        String calleeId = "13100001111";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String callTime = simpleDateFormat.format(new Date());

        DecimalFormat durationFormat = new DecimalFormat("0000");
        String duration = durationFormat.format(new Random().nextInt(10000));

        String hash = RowKeyUtils.getReNo(callerId, callTime);

        String rowKey = hash + "," + callerId + "," + callTime + "," + "0," + calleeId + "," + duration;
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("callerPos"), Bytes.toBytes("河南"));
        put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("calleePos"), Bytes.toBytes("河北"));

        table.put(put);
        System.out.println("put ok!");
    }

}
