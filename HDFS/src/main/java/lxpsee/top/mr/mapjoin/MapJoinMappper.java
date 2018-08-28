package main.java.lxpsee.top.mr.mapjoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/27 07:15.
 */
public class MapJoinMappper extends Mapper<LongWritable, Text, Text, NullWritable> {
    private Map<String, String> allCustomers = new HashMap<String, String>();

    /**
     * 启动，初始化客户信息到map中
     * 1.通过上下文对象获取配置文件，获取到FileSystem对象
     * 2.FileSystem获取对指定文件的输入流
     * 3.创建bufferedReader对象
     * 4.整行读入，获取每一行的id作为key，整行作为value存入集合中
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        Configuration configuration = context.getConfiguration();
        FileSystem fileSystem = FileSystem.get(configuration);
        FSDataInputStream fsDataInputStream = fileSystem.open(new Path("file:///D:/workDir/join/customers.txt"));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fsDataInputStream));
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String cid = line.substring(0, line.indexOf(","));
            allCustomers.put(cid, line);
        }

    }

    /**
     * 读取一行，key为偏移量，value是每一行文件的内容
     * 获取订单内容，cid，订单信息和客户信息拼接写回
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String cid = line.substring(line.lastIndexOf(",") + 1);
        String ordersInfo = line.substring(0, line.lastIndexOf(","));
        context.write(new Text(allCustomers.get(cid) + "," + ordersInfo), NullWritable.get());
    }
}
