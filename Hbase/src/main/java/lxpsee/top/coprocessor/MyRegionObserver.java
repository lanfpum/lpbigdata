package lxpsee.top.coprocessor;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/4 17:35.
 */
public class MyRegionObserver extends BaseRegionObserver {
    public void outInfo(String s) {
        try {
            FileWriter fileWriter = new FileWriter("/home/lanp/coprocessor.txt", true);
            fileWriter.write(s + "\r\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(CoprocessorEnvironment e) throws IOException {
        super.start(e);
        outInfo("LP : MyRegionObserver.start() method executing ..........");
    }

    @Override
    public void stop(CoprocessorEnvironment e) throws IOException {
        super.stop(e);
        outInfo("LP : MyRegionObserver.stop() method executing !!!!!!!!!!");
    }

    @Override
    public void preOpen(ObserverContext<RegionCoprocessorEnvironment> e) throws IOException {
        super.preOpen(e);
        outInfo("LP : MyRegionObserver.preOpen() method executing ..........");
    }

    @Override
    public void postOpen(ObserverContext<RegionCoprocessorEnvironment> e) {
        super.postOpen(e);
        outInfo("LP : MyRegionObserver.postOpen() method executing ..........!~!!");
    }

    @Override
    public void preGetOp(ObserverContext<RegionCoprocessorEnvironment> e, Get get, List<Cell> results) throws IOException {
        super.preGetOp(e, get, results);
        outInfo("LP : MyRegionObserver.preGet() method,and rowKey =  " + Bytes.toString(get.getRow()));
    }

    @Override
    public void postGetOp(ObserverContext<RegionCoprocessorEnvironment> e, Get get, List<Cell> results) throws IOException {
        super.postGetOp(e, get, results);
        outInfo("LP : MyRegionObserver.postGet() method, rowKey = " + Bytes.toString(get.getRow()));
    }

    @Override
    public void prePut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        super.prePut(e, put, edit, durability);
        outInfo("LP : MyRegionObserver.prePut(),and rowKey =  " + Bytes.toString(put.getRow()));
    }

    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        super.postPut(e, put, edit, durability);
        outInfo("LP : MyRegionObserver.postPut() method, rowKey = " + Bytes.toString(put.getRow()));
    }

    @Override
    public void preDelete(ObserverContext<RegionCoprocessorEnvironment> e, Delete delete, WALEdit edit, Durability durability) throws IOException {
        super.preDelete(e, delete, edit, durability);
        outInfo("LP : MyRegionObserver.preDelete() method ,and rowKey = " + Bytes.toString(delete.getRow()));
    }

    @Override
    public void postDelete(ObserverContext<RegionCoprocessorEnvironment> e, Delete delete, WALEdit edit, Durability durability) throws IOException {
        super.postDelete(e, delete, edit, durability);
        outInfo("LP : MyRegionObserver.postDelete() method ,and rowKey = " + Bytes.toString(delete.getRow()));
    }
}
