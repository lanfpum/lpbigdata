package main.java.lxpsee.top.mr.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/16 10:37.
 */
public class WriteTxt {

    @Test
    public void writeTxt() {
        File file;
        FileWriter fw = null;
        file = new File("D:\\workDir\\maxtemp\\txt\\1.txt");
        try {

            if (!file.exists()) {
                file.createNewFile();
            }

            fw = new FileWriter(file);
            Random random = new Random();

            for (int i = 1; i <= 8000; i++) {
                // 写入 1970 年开始到2018年，随机温度
                fw.write((random.nextInt(48) + 1970) + " " + (random.nextInt(100) + -30) + "\r\n");
                fw.flush();
            }

            System.out.println("写数据成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void writeSeq() throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "file:///");
        FileSystem fileSystem = FileSystem.get(configuration);
        Path path = new Path("D:\\workDir\\maxtemp\\seq\\1.seq");

        SequenceFile.Writer writer = SequenceFile.createWriter(fileSystem, configuration, path, IntWritable.class, IntWritable.class);
        Random random = new Random();

        for (int i = 1; i <= 8000; i++) {
            // 写入 1970 年开始到2018年，随机温度
            writer.append(new IntWritable(random.nextInt(48) + 1970), new IntWritable((random.nextInt(100) + -30)));
        }

        writer.close();
        System.out.println("写数据成功！");
    }


}
