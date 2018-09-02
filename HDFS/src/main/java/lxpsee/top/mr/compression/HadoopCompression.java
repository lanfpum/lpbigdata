package lxpsee.top.mr.compression;

import com.hadoop.compression.lzo.LzoCodec;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/9 10:23.
 */

public class HadoopCompression {
    public static void main(String[] args) throws IOException {
        Class[] classes = {
                DeflateCodec.class,
                GzipCodec.class,
                BZip2Codec.class,
                Lz4Codec.class,
                SnappyCodec.class,
                LzoCodec.class
        };

        for (Class aClass : classes) {
            zip(aClass);
        }

        System.out.println("-------------------------------------------------");

        for (Class aClass : classes) {
            unZip(aClass);
        }

    }

    private static void zip(Class clazz) throws IOException {
        long start = System.currentTimeMillis();
        CompressionCodec compressionCodec = (CompressionCodec) ReflectionUtils.newInstance(clazz, new Configuration());
        FileOutputStream fileOutputStream = new FileOutputStream("/home/lanp/zip/b" + compressionCodec.getDefaultExtension());
        CompressionOutputStream outputStream = compressionCodec.createOutputStream(fileOutputStream);
        IOUtils.copyBytes(new FileInputStream("/home/lanp/zip/a.txt"), outputStream, 1024);
        outputStream.close();
        System.out.println(clazz.getSimpleName() + " 压缩所需时间 : " + (System.currentTimeMillis() - start));
    }

    private static void unZip(Class clazz) throws IOException {
        long start = System.currentTimeMillis();
        CompressionCodec compressionCodec = (CompressionCodec) ReflectionUtils.newInstance(clazz, new Configuration());
        FileInputStream fileInputStream = new FileInputStream("/home/lanp/zip/b" + compressionCodec.getDefaultExtension());
        CompressionInputStream compressionCodecInputStream = compressionCodec.createInputStream(fileInputStream);
        IOUtils.copyBytes(compressionCodecInputStream, new FileOutputStream("/home/lanp/zip/a" + clazz.getSimpleName() + ".txt"), 1024);
        compressionCodecInputStream.close();
        System.out.println(clazz.getSimpleName() + " 解压所需时间 : " + (System.currentTimeMillis() - start));
    }
}
