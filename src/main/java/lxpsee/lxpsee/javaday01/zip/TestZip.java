package lxpsee.lxpsee.javaday01.zip;


import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 努力常态化  2018/6/26 10:16 The world always makes way for the dreamer
 * 压缩流的用法
 */
public class TestZip {

    public static void main(String[] args) throws IOException {
//        zip();
        unZip();
    }

    /**
     * 解压
     */
    private static void unZip() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("F:\\bigdata\\arch\\a.zip");
        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
        ZipEntry zipEntry;
        FileOutputStream fileOutputStream;
        byte[] bytes = new byte[1024];
        int len;

        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            fileOutputStream = new FileOutputStream("F:\\bigdata\\arch\\unzip\\" + zipEntry.getName());

            while ((len = zipInputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }

            fileOutputStream.close();
        }

        zipInputStream.close();
        fileInputStream.close();

    }

    /**
     * 压缩
     *
     * @throws IOException
     */
    private static void zip() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("F:\\bigdata\\arch\\a.xar");
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

        String[] arr = {"F:\\bigdata\\arch\\a.txt", "F:\\bigdata\\arch\\b.png"};
        for (String s : arr) {
            zipFile(zipOutputStream, s);
        }

        zipOutputStream.close();
        fileOutputStream.close();
        System.out.println("over");
    }

    /**
     * 循环向压缩流中输出条目
     *
     * @param zipOutputStream
     * @param path
     * @throws IOException
     */
    private static void zipFile(ZipOutputStream zipOutputStream, String path) throws IOException {
        File file = new File(path);
        zipOutputStream.putNextEntry(new ZipEntry(file.getName()));

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);
        fileInputStream.close();

        zipOutputStream.write(bytes);
    }

    @Test
    public void run() {
        while (true) {
            byte[] b = new byte[1024];
        }
    }

}
