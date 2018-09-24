package lxpsee.top.test;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;


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

    @Test
    public void bigInsert() throws IOException {
        long start = System.currentTimeMillis();
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        TableName tableName = TableName.valueOf("ns1:t1");
        HTable table = (HTable) connection.getTable(tableName);
        table.setAutoFlush(false);
        int count = 1;
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("00000");

        for (int i = 1; i <= 10000; i++) {
            Put put = new Put(Bytes.toBytes("row" + format.format(i)));
            put.setWriteToWAL(false);
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("id"), Bytes.toBytes(i));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"), Bytes.toBytes("tom" + i));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("age"), Bytes.toBytes(i % 100));
            table.put(put);

            if (i % 2000 == 0) {
                table.flushCommits();
                System.out.println("20000 OK!!   " + count++);
            }
        }

        table.flushCommits();
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void createNameSpace() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Admin admin = connection.getAdmin();
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create("ns2").build();
        admin.createNamespace(namespaceDescriptor);
        System.out.println("名称空间已经创建ok!");
    }

    @Test
    public void showAllNameSpace() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Admin admin = connection.getAdmin();
        NamespaceDescriptor[] namespaceDescriptors = admin.listNamespaceDescriptors();

        for (NamespaceDescriptor namespaceDescriptor : namespaceDescriptors) {
            System.out.println(namespaceDescriptor.getName());
        }

    }

    @Test
    public void createTable() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Admin admin = connection.getAdmin();
        TableName tableName = TableName.valueOf("ns2:t1");
        HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
        hTableDescriptor.addFamily(new HColumnDescriptor("f1"));
        admin.createTable(hTableDescriptor);
        System.out.println("over");
    }


    @Test
    public void formatNum() {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("00000");
        System.out.println(format.format(1.59));
    }

}
