package lxpsee.lxpsee.hadoopday03;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/2 12:24.
 * <p>
 * 测试HDFS,完成hdfs操作
 */
public class HadoopTestHDFS {


    /**
     * 读取hdfs文件
     * <p>
     * 直接通过java的URL获取流，需要引入Hadoop的jar包
     */
    @Test
    public void readFile() throws IOException {
        //注册url流处理器工厂(hdfs)
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());

        URL url = new URL("hdfs://192.168.68.201:8020/user/lanp/hadoop/1.txt");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();
        System.out.println(new String(bytes));
    }

    /**
     * 通过hadoop API访问文件
     * <p>
     * 通过 Hadoop 的FileSystem获取流，需要制定配置文件和路径
     */
    @Test
    public void readFileByAPI() throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://192.168.68.201:8020/");
        FileSystem fileSystem = FileSystem.get(configuration);
        Path path = new Path("/user/lanp/hadoop/1.txt");
        FSDataInputStream fsDataInputStream = fileSystem.open(path);
        byte[] buf = new byte[1024];
        int len;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        while ((len = fsDataInputStream.read(buf)) != -1) {
            byteArrayOutputStream.write(buf, 0, len);
        }

        byteArrayOutputStream.close();
        fsDataInputStream.close();

        System.out.println(new String(byteArrayOutputStream.toByteArray()));
    }

    @Test
    public void readFileByAPI2() throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://192.168.68.201:8020/");
        FileSystem fileSystem = FileSystem.get(configuration);
        Path path = new Path("/user/lanp/hadoop/1.txt");
        FSDataInputStream fsDataInputStream = fileSystem.open(path);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        IOUtils.copyBytes(fsDataInputStream, byteArrayOutputStream, 1024);

        System.out.println(new String(byteArrayOutputStream.toByteArray()));
    }

    @Test
    public void mkdir() throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://192.168.68.201:8020/");
        FileSystem fileSystem = FileSystem.get(configuration);
        fileSystem.mkdirs(new Path("/user/lanp/myhadoop/"));
    }

    @Test
    public void putFile() throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://192.168.68.201:8020/");
        FileSystem fileSystem = FileSystem.get(configuration);
        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/user/lanp/myhadoop/2.txt"));
        fsDataOutputStream.write("hello,mylittleeping".getBytes());
        fsDataOutputStream.close();
    }

    @Test
    public void deleteFile() throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://192.168.68.201:8020/");
        FileSystem fileSystem = FileSystem.get(configuration);
        fileSystem.delete(new Path("/user/lanp/myhadoop"), true);
    }

    @Test
    public void readFile2() throws IOException {
        //注册url流处理器工厂(hdfs)
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());

        URL url = new URL("hdfs://192.168.68.201:8020/user/lanp/myhadoop/2.txt");
        InputStream inputStream = url.openStream();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();

        System.out.println(new String(bytes));
    }

}
