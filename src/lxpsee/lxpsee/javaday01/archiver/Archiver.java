package lxpsee.lxpsee.javaday01.archiver;


import top.lxpsee.javaday01.archiver.utils.FileUtils;

import java.io.*;

/**
 * 努力常态化  2018/6/24 15:07 The world always makes way for the dreamer
 * 归档器
 */
public class Archiver {

    public static void main(String[] args) throws IOException {
        FileOutputStream fo = new FileOutputStream("F:/bigdata/arch/x.xar");
        fo.write(addFile("F:/bigdata/arch/a.txt"));
        fo.write(addFile("F:/bigdata/arch/b.png"));
        fo.close();
    }

    private static byte[] addFile(String pathName) throws IOException {
        //文件
        File file = new File(pathName);
        //文件名
        String fileName = file.getName();
        //文件名数组
        byte[] fileNameBytes = fileName.getBytes();
        //文件内容长度
        int len = (int) file.length();
        //计算总长度
        int total = 4 + fileNameBytes.length + 4 + len;

        //初始化总数组
        byte[] arr = new byte[total];
        //1.写入文件名长度
        System.arraycopy(FileUtils.int2Bytes(fileNameBytes.length), 0, arr, 0, 4);
        //2.写入文件名本身
        System.arraycopy(fileNameBytes, 0, arr, 4, fileNameBytes.length);
        //3.写入文件内容长度
        System.arraycopy(FileUtils.int2Bytes(len), 0, arr, 4 + fileNameBytes.length, 4);

        //4.写入文件内容
        //读取文件内容到数组中
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int len0 = 0;

        while ((len0 = fis.read(bytes)) != -1) {
            bos.write(bytes, 0, len0);
        }

        bos.close();

        //得到文件内容
        byte[] fileContent = bos.toByteArray();

        System.arraycopy(fileContent, 0, arr, 4 + fileNameBytes.length + 4, fileContent.length);
        return arr;
    }

}
