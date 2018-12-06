package lxpsee.top.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 读取文件
 **/
public class ReadHdfsFile {
    public static BufferedReader fileReader(String fileName) throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        FSDataInputStream in = fs.open(new Path(fileName));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        return br;
    }
}
