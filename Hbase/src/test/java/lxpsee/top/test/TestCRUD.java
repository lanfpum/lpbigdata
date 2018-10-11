package lxpsee.top.test;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

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
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create("ns1").build();
        admin.createNamespace(namespaceDescriptor);
        System.out.println("名称空间已经创建ok!");
    }


    @Test
    public void deleteNameSpace() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Admin admin = connection.getAdmin();
        admin.deleteNamespace("ns2");
        System.out.println("over");
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
        TableName tableName = TableName.valueOf("ns1:t1");
        HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
        hTableDescriptor.addFamily(new HColumnDescriptor("f1"));
        admin.createTable(hTableDescriptor);
        System.out.println("over");
    }

    @Test
    public void createTable2() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Admin admin = connection.getAdmin();
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf("ns1:t2"));
        hTableDescriptor.addFamily(new HColumnDescriptor("f1"));
        String[] split = {"row01000", "row05000"};
        admin.createTable(hTableDescriptor, Bytes.toByteArrays(split));
        System.out.println("over");
    }

    @Test
    public void getWithVersions() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t3"));
        Get get = new Get(Bytes.toBytes("row1"));
//        get.setMaxVersions(10);
        get.setMaxVersions();
        Result result = table.get(get);
        List<Cell> columnCells = result.getColumnCells(Bytes.toBytes("f1"), Bytes.toBytes("name"));

        for (Cell columnCell : columnCells) {
            String f = Bytes.toString(columnCell.getFamily());
            String col = Bytes.toString(columnCell.getQualifier());
            long timestamp = columnCell.getTimestamp();
            String val = Bytes.toString(columnCell.getValue());
            System.out.println(f + "/" + col + "/" + timestamp + " : " + val);
        }
    }


    @Test
    public void disableTable() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Admin admin = connection.getAdmin();
        admin.disableTable(TableName.valueOf("ns2:t1"));
//        admin.enableTable(TableName.valueOf("ns1:t1"));
        admin.deleteTable(TableName.valueOf("ns2:t1"));
        System.out.println("over");
    }

    @Test
    public void deleteData() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t1"));
        Delete delete = new Delete(Bytes.toBytes("row00001"));
//        delete.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("id"));
//        delete.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("age"));
        table.delete(delete);
        System.out.println("over");
    }

    @Test
    public void scan() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t1"));
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes("row02000"));
        scan.setStopRow(Bytes.toBytes("row05000"));
        ResultScanner resultScanner = table.getScanner(scan);
        Iterator<Result> iterator = resultScanner.iterator();

        while (iterator.hasNext()) {
            Result result = iterator.next();
            byte[] value = result.getValue(Bytes.toBytes("f1"), Bytes.toBytes("name"));
            System.out.println(Bytes.toString(value));
        }
    }

    @Test
    public void scan2() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t1"));
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes("row03000"));
        scan.setStopRow(Bytes.toBytes("row04000"));
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            Map<byte[], byte[]> f1Map = next.getFamilyMap(Bytes.toBytes("f1"));

            for (Map.Entry<byte[], byte[]> entry : f1Map.entrySet()) {
                String col = Bytes.toString(entry.getKey());
                String val = Bytes.toString(entry.getValue());
                System.out.print(col + " : " + val + ";");
            }

            System.out.println();
        }
    }

    @Test
    public void scan3() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t6"));
        Scan scan = new Scan();
//        scan.setStartRow(Bytes.toBytes("row04000"));
//        scan.setStopRow(Bytes.toBytes("row05000"));
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            //得到一行的所有map,key=f1,value=Map<Col,Map<Timestamp,value>>
            Map<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> rowMap = next.getMap();

            for (Map.Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> rowMapEntry : rowMap.entrySet()) {
                String f = Bytes.toString(rowMapEntry.getKey());
                Map<byte[], NavigableMap<Long, byte[]>> colData = rowMapEntry.getValue();

                for (Map.Entry<byte[], NavigableMap<Long, byte[]>> colDataEntry : colData.entrySet()) {
                    String col = Bytes.toString(colDataEntry.getKey());
                    Map<Long, byte[]> tsData = colDataEntry.getValue();

                    for (Map.Entry<Long, byte[]> longEntry : tsData.entrySet()) {
                        Long key = longEntry.getKey();
                        String val = Bytes.toString(longEntry.getValue());
                        System.out.print(f + " - " + col + " - " + key + " : " + val + ";");
                    }
                }
            }

            System.out.println();
        }
    }

    @Test
    public void formatNum() {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("00000");
        System.out.println(format.format(1.59));
    }

    @Test
    public void getScanCache() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Scan scan = new Scan();
        scan.setCaching(1);
        Table table = connection.getTable(TableName.valueOf("ns1:t1"));
        ResultScanner scanner = table.getScanner(scan);
        System.out.println(scan.getCaching());
        long start = System.currentTimeMillis();
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result result = iterator.next();
            System.out.println(result.getColumnLatest(Bytes.toBytes("f1"), Bytes.toBytes("name")));
        }

        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testBatchAndCaching() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t6"));
        Scan scan = new Scan();
//        scan.setCaching(3);
        scan.setBatch(4);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            System.out.println("--------------------------------------------------------------------");
            Result next = iterator.next();
            NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = next.getMap();
            for (Map.Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> entry : map.entrySet()) {
                String f = Bytes.toString(entry.getKey());
                NavigableMap<byte[], NavigableMap<Long, byte[]>> colData = entry.getValue();

                for (Map.Entry<byte[], NavigableMap<Long, byte[]>> tsEntry : colData.entrySet()) {
                    String col = Bytes.toString(tsEntry.getKey());
                    NavigableMap<Long, byte[]> valueEntry = tsEntry.getValue();

                    for (Map.Entry<Long, byte[]> longEntry : valueEntry.entrySet()) {
                        Long ts = longEntry.getKey();
                        String val = Bytes.toString(longEntry.getValue());
                        System.out.print(f + "/" + col + "/" + ts + ":" + val);
                    }
                }
            }

            System.out.println();
        }
    }

    @Test
    public void testRowFilter() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t1"));
        Scan scan = new Scan();
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes("row00100")));
        scan.setFilter(rowFilter);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            System.out.println(Bytes.toString(next.getRow()));
        }
    }

    @Test
    public void testFamilyFilter() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t6"));
        Scan scan = new Scan();
        FamilyFilter familyFilter = new FamilyFilter(CompareFilter.CompareOp.LESS, new BinaryComparator(Bytes.toBytes("f2")));
        scan.setFilter(familyFilter);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            byte[] f1id = next.getValue(Bytes.toBytes("f1"), Bytes.toBytes("id"));
            byte[] f2id = next.getValue(Bytes.toBytes("f2"), Bytes.toBytes("id"));
            System.out.println(Bytes.toString(f1id) + "  :  " + Bytes.toString(f2id));
        }
    }

    @Test
    public void testColFilter() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t6"));
        Scan scan = new Scan();
        QualifierFilter qualifierFilter = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("id")));
        scan.setFilter(qualifierFilter);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            byte[] f1id = next.getValue(Bytes.toBytes("f1"), Bytes.toBytes("id"));
            byte[] f2id = next.getValue(Bytes.toBytes("f2"), Bytes.toBytes("id"));
            byte[] f2Name = next.getValue(Bytes.toBytes("f2"), Bytes.toBytes("name"));
            System.out.println(Bytes.toString(f1id) + "  :  " + Bytes.toString(f2id) + " : " + f2Name);
        }
    }

    @Test
    public void testValueFilter() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t6"));
        Scan scan = new Scan();
        ValueFilter valueFilter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("t"));
        scan.setFilter(valueFilter);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            byte[] f1id = next.getValue(Bytes.toBytes("f1"), Bytes.toBytes("id"));
            byte[] f2id = next.getValue(Bytes.toBytes("f2"), Bytes.toBytes("id"));
            byte[] f1Name = next.getValue(Bytes.toBytes("f1"), Bytes.toBytes("name"));
            byte[] f2Name = next.getValue(Bytes.toBytes("f2"), Bytes.toBytes("name"));
            System.out.println(Bytes.toString(f1id) + "  :  " + Bytes.toString(f2id) +
                    " : " + Bytes.toString(f2Name) + " : " + Bytes.toString(f1Name));
        }
    }

    /**
     * 依赖列过滤器。
     * null  :  null : null : null
     * null  :  null : null : null
     */
    @Test
    public void testDepFilter() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t6"));
        Scan scan = new Scan();
        DependentColumnFilter dependentColumnFilter = new DependentColumnFilter(Bytes.toBytes("f2"),
                Bytes.toBytes("addr"), false, CompareFilter.CompareOp.NOT_EQUAL,
                new BinaryComparator(Bytes.toBytes("hechi")));
        scan.setFilter(dependentColumnFilter);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            byte[] f1id = next.getValue(Bytes.toBytes("f1"), Bytes.toBytes("id"));
            byte[] f2id = next.getValue(Bytes.toBytes("f2"), Bytes.toBytes("id"));
            byte[] f1Name = next.getValue(Bytes.toBytes("f1"), Bytes.toBytes("name"));
            byte[] f2Name = next.getValue(Bytes.toBytes("f2"), Bytes.toBytes("name"));
            System.out.println(Bytes.toString(f1id) + "  :  " + Bytes.toString(f2id) +
                    " : " + Bytes.toString(f2Name) + " : " + Bytes.toString(f1Name));
        }
    }

    /**
     * 单列值排除过滤器,去掉过滤使用的列,对列的值进行过滤
     * 2  :  2.1 : null : lp
     * 3  :  13 : null : gxp
     */
    @Test
    public void testSingleColumValueFilter() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t6"));
        Scan scan = new Scan();
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueExcludeFilter(
                Bytes.toBytes("f2"), Bytes.toBytes("name"), CompareFilter.CompareOp.NOT_EQUAL,
                new BinaryComparator(Bytes.toBytes("tom1.1")));
        scan.setFilter(singleColumnValueFilter);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            byte[] f1id = next.getValue(Bytes.toBytes("f1"), Bytes.toBytes("id"));
            byte[] f2id = next.getValue(Bytes.toBytes("f2"), Bytes.toBytes("id"));
            byte[] f1Name = next.getValue(Bytes.toBytes("f1"), Bytes.toBytes("name"));
            byte[] f2Name = next.getValue(Bytes.toBytes("f2"), Bytes.toBytes("name"));
            System.out.println(Bytes.toString(f1id) + "  :  " + Bytes.toString(f2id) +
                    " : " + Bytes.toString(f2Name) + " : " + Bytes.toString(f1Name));
        }
    }

    @Test
    public void testSingleColumValueExcludeFilter() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t6"));
        Scan scan = new Scan();
        SingleColumnValueExcludeFilter singleColumnValueFilter = new SingleColumnValueExcludeFilter(
                Bytes.toBytes("f2"), Bytes.toBytes("name"), CompareFilter.CompareOp.NOT_EQUAL,
                new BinaryComparator(Bytes.toBytes("tom2.1"))
        );
        scan.setFilter(singleColumnValueFilter);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            byte[] f1id = next.getValue(Bytes.toBytes("f1"), Bytes.toBytes("id"));
            byte[] f2id = next.getValue(Bytes.toBytes("f2"), Bytes.toBytes("id"));
            byte[] f1Name = next.getValue(Bytes.toBytes("f1"), Bytes.toBytes("name"));
            byte[] f2Name = next.getValue(Bytes.toBytes("f2"), Bytes.toBytes("name"));
            System.out.println(Bytes.toString(f1id) + "  :  " + Bytes.toString(f2id) +
                    " : " + Bytes.toString(f2Name) + " : " + Bytes.toString(f1Name));
        }
    }

    @Test
    public void testPrefixFilter() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t1"));
        Scan scan = new Scan();
        PrefixFilter prefixFilter = new PrefixFilter(Bytes.toBytes("row0000"));
        scan.setFilter(prefixFilter);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            //得到一行的所有map,key=f1,value=Map<Col,Map<Timestamp,value>>
            Map<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> rowMap = next.getMap();

            for (Map.Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> rowMapEntry : rowMap.entrySet()) {
                String f = Bytes.toString(rowMapEntry.getKey());
                Map<byte[], NavigableMap<Long, byte[]>> colData = rowMapEntry.getValue();

                for (Map.Entry<byte[], NavigableMap<Long, byte[]>> colDataEntry : colData.entrySet()) {
                    String col = Bytes.toString(colDataEntry.getKey());
                    Map<Long, byte[]> tsData = colDataEntry.getValue();

                    for (Map.Entry<Long, byte[]> longEntry : tsData.entrySet()) {
                        Long key = longEntry.getKey();
                        String val = Bytes.toString(longEntry.getValue());
                        System.out.print(f + " - " + col + " - " + key + " : " + val + ";");
                    }
                }
            }

            System.out.println();
        }
    }

    /**
     * 分页过滤,是rowkey过滤,在region上扫描时，对每次page设置的大小。
     * 返回到到client，设计到每个Region结果的合并。
     */
    @Test
    public void testOageFilter() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t1"));
        Scan scan = new Scan();
        PageFilter pageFilter = new PageFilter(10);
        scan.setFilter(pageFilter);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            //得到一行的所有map,key=f1,value=Map<Col,Map<Timestamp,value>>
            Map<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> rowMap = next.getMap();

            for (Map.Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> rowMapEntry : rowMap.entrySet()) {
                String f = Bytes.toString(rowMapEntry.getKey());
                Map<byte[], NavigableMap<Long, byte[]>> colData = rowMapEntry.getValue();

                for (Map.Entry<byte[], NavigableMap<Long, byte[]>> colDataEntry : colData.entrySet()) {
                    String col = Bytes.toString(colDataEntry.getKey());
                    Map<Long, byte[]> tsData = colDataEntry.getValue();

                    for (Map.Entry<Long, byte[]> longEntry : tsData.entrySet()) {
                        Long key = longEntry.getKey();
                        String val = Bytes.toString(longEntry.getValue());
                        System.out.print(f + " - " + col + " - " + key + " : " + val + ";");
                    }
                }
            }

            System.out.println();
        }
    }

    @Test
    public void testKeyOnlyFilter() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t6"));
        Scan scan = new Scan();
        KeyOnlyFilter keyOnlyFilter = new KeyOnlyFilter(true);
        scan.setFilter(keyOnlyFilter);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            //得到一行的所有map,key=f1,value=Map<Col,Map<Timestamp,value>>
            Map<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> rowMap = next.getMap();

            for (Map.Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> rowMapEntry : rowMap.entrySet()) {
                String f = Bytes.toString(rowMapEntry.getKey());
                Map<byte[], NavigableMap<Long, byte[]>> colData = rowMapEntry.getValue();

                for (Map.Entry<byte[], NavigableMap<Long, byte[]>> colDataEntry : colData.entrySet()) {
                    String col = Bytes.toString(colDataEntry.getKey());
                    Map<Long, byte[]> tsData = colDataEntry.getValue();

                    for (Map.Entry<Long, byte[]> longEntry : tsData.entrySet()) {
                        Long key = longEntry.getKey();
                        String val = Bytes.toString(longEntry.getValue());
                        System.out.print(f + " - " + col + " - " + key + " : " + val + ";");
                    }
                }
            }

            System.out.println();
        }
    }

    @Test
    public void testColumPageFilter() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t6"));
        Scan scan = new Scan();
        ColumnPaginationFilter columnPaginationFilter = new ColumnPaginationFilter(3, 0);
        scan.setFilter(columnPaginationFilter);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            //得到一行的所有map,key=f1,value=Map<Col,Map<Timestamp,value>>
            Map<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> rowMap = next.getMap();

            for (Map.Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> rowMapEntry : rowMap.entrySet()) {
                String f = Bytes.toString(rowMapEntry.getKey());
                Map<byte[], NavigableMap<Long, byte[]>> colData = rowMapEntry.getValue();

                for (Map.Entry<byte[], NavigableMap<Long, byte[]>> colDataEntry : colData.entrySet()) {
                    String col = Bytes.toString(colDataEntry.getKey());
                    Map<Long, byte[]> tsData = colDataEntry.getValue();

                    for (Map.Entry<Long, byte[]> longEntry : tsData.entrySet()) {
                        Long key = longEntry.getKey();
                        String val = Bytes.toString(longEntry.getValue());
                        System.out.print(f + " - " + col + " - " + key + " : " + val + ";");
                    }
                }
            }

            System.out.println();
        }
    }

    @Test
    public void testLike() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t6"));
        Scan scan = new Scan();
        ValueFilter valueFilter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("^tom2"));
        scan.setFilter(valueFilter);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            //得到一行的所有map,key=f1,value=Map<Col,Map<Timestamp,value>>
            Map<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> rowMap = next.getMap();

            for (Map.Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> rowMapEntry : rowMap.entrySet()) {
                String f = Bytes.toString(rowMapEntry.getKey());
                Map<byte[], NavigableMap<Long, byte[]>> colData = rowMapEntry.getValue();

                for (Map.Entry<byte[], NavigableMap<Long, byte[]>> colDataEntry : colData.entrySet()) {
                    String col = Bytes.toString(colDataEntry.getKey());
                    Map<Long, byte[]> tsData = colDataEntry.getValue();

                    for (Map.Entry<Long, byte[]> longEntry : tsData.entrySet()) {
                        Long key = longEntry.getKey();
                        String val = Bytes.toString(longEntry.getValue());
                        System.out.print(f + " - " + col + " - " + key + " : " + val + ";");
                    }
                }
            }

            System.out.println();
        }
    }

    /**
     * select * from t6 where ((age < 12) and (name like 'to%')) or ((age > 12) and (name like '%to'))
     */
    @Test
    public void testComboKey() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t6"));
        Scan scan = new Scan();

        SingleColumnValueFilter f2Age1 = new SingleColumnValueFilter(
                Bytes.toBytes("f2"),
                Bytes.toBytes("age"),
                CompareFilter.CompareOp.LESS_OR_EQUAL,
                new BinaryComparator(Bytes.toBytes("12")));
        SingleColumnValueFilter f2Name1 = new SingleColumnValueFilter(
                Bytes.toBytes("f2"),
                Bytes.toBytes("name"),
                CompareFilter.CompareOp.EQUAL,
                new RegexStringComparator("^to"));
        FilterList filterList1 = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList1.addFilter(f2Age1);
        filterList1.addFilter(f2Name1);

        SingleColumnValueFilter f2Age2 = new SingleColumnValueFilter(
                Bytes.toBytes("f2"),
                Bytes.toBytes("age"),
                CompareFilter.CompareOp.GREATER,
                new BinaryComparator(Bytes.toBytes("13")));
        SingleColumnValueFilter f2Name2 = new SingleColumnValueFilter(
                Bytes.toBytes("f2"),
                Bytes.toBytes("name"),
                CompareFilter.CompareOp.EQUAL,
                new RegexStringComparator("to$"));
        FilterList filterList2 = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        filterList2.addFilter(f2Age2);
        filterList2.addFilter(f2Name2);

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        filterList.addFilter(filterList1);
        filterList.addFilter(filterList2);

        scan.setFilter(filterList);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();

        while (iterator.hasNext()) {
            Result next = iterator.next();
            //得到一行的所有map,key=f1,value=Map<Col,Map<Timestamp,value>>
            Map<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> rowMap = next.getMap();

            for (Map.Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> rowMapEntry : rowMap.entrySet()) {
                String f = Bytes.toString(rowMapEntry.getKey());
                Map<byte[], NavigableMap<Long, byte[]>> colData = rowMapEntry.getValue();

                for (Map.Entry<byte[], NavigableMap<Long, byte[]>> colDataEntry : colData.entrySet()) {
                    String col = Bytes.toString(colDataEntry.getKey());
                    Map<Long, byte[]> tsData = colDataEntry.getValue();

                    for (Map.Entry<Long, byte[]> longEntry : tsData.entrySet()) {
                        Long key = longEntry.getKey();
                        String val = Bytes.toString(longEntry.getValue());
                        System.out.print(f + "/" + col + "/" + key + ":" + val + ";");
                    }
                }
            }

            System.out.println();
        }
    }

    @Test
    public void testIncr() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(configuration);
        Table table = connection.getTable(TableName.valueOf("ns1:t7"));
        Increment increment = new Increment(Bytes.toBytes("row1"));
        increment.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("daily"), 1);
        increment.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("weekly"), 2);
        increment.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("monthly"), 5);
        table.increment(increment);
        System.out.println("ok");
    }


}
