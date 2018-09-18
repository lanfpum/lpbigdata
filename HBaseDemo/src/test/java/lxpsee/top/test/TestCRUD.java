package lxpsee.top.test;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;


/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/9/13 16:40.
 */
public class TestCRUD {

    /**
     * 测试hbase的插入数据
     * 创建配置类对象，连接工厂获取连接，连接通过表名获得表对象，创建插入对象，设置插入对象的属性
     * 表执行插入
     */
    @Test
    public void put() throws Exception {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        TableName tableName = TableName.valueOf("ns1:t1");
        Table table = connection.getTable(tableName);
        Put put = new Put(Bytes.toBytes("row2"));
        put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("age"), Bytes.toBytes(110));
        table.put(put);
        System.out.println("ok");
    }

    @Test
    public void get() throws Exception {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        TableName tableName = TableName.valueOf("ns1:t1");
        Table table = connection.getTable(tableName);

        Get get = new Get(Bytes.toBytes("row2"));
        Result result = table.get(get);
        byte[] value = result.getValue(Bytes.toBytes("f1"), Bytes.toBytes("age"));
        System.out.println(Bytes.toInt(value));
    }


}
