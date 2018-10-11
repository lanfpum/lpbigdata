package lxpsee.top.coprocessor;

import lxpsee.top.utils.RowKeyUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/8 12:55.
 */
public class CalleeLogRegionObserver extends BaseRegionObserver {
    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        super.postPut(e, put, edit, durability);
        TableName tableName = TableName.valueOf("ns1:calllogs");
        String tablename1 = tableName.getNameAsString();
        String nameAsString = e.getEnvironment().getRegion().getRegionInfo().getTable().getNameAsString();

        if (!tablename1.equals(nameAsString)) {
            return;
        }

        String[] rowKeyArr = Bytes.toString(put.getRow()).split(",");

        if (rowKeyArr[3].equals("1")) {
            return;
        }


        String reNo = RowKeyUtils.getReNo(rowKeyArr[4], rowKeyArr[2]);
        String rowKey = reNo + "," + rowKeyArr[4] + "," + rowKeyArr[2] + ",1," + rowKeyArr[1] + rowKeyArr[5];
        Put calleePut = new Put(Bytes.toBytes(rowKey));
        calleePut.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("flag"), Bytes.toBytes("no"));

        Table table = e.getEnvironment().getTable(tableName);
        table.put(calleePut);
        System.out.println("calleePut is ok! ");
    }
}
