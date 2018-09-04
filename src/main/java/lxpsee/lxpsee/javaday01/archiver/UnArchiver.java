package lxpsee.lxpsee.javaday01.archiver;

import top.lxpsee.javaday01.archiver.model.FileBean;
import top.lxpsee.javaday01.archiver.utils.FileUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 努力常态化  2018/6/26 8:52 The world always makes way for the dreamer
 * 解档程序
 */
public class UnArchiver {

    public static void main(String[] args) throws IOException {
        List<FileBean> fileBeanList = new ArrayList<FileBean>();
        FileInputStream fileInputStream = new FileInputStream("F:\\bigdata\\arch\\x.xar");
        FileBean fileBean;

        while ((fileBean = getNextFile(fileInputStream)) != null) {
            fileBeanList.add(fileBean);
        }

        fileInputStream.close();

        FileOutputStream fileOutputStream;

        for (FileBean bean : fileBeanList) {
            fileOutputStream = new FileOutputStream("F:\\bigdata\\arch\\unarch\\" + bean.getFileName());
            fileOutputStream.write(bean.getFileContentBytes());
            fileOutputStream.close();
        }

    }

    /**
     * 从流中读取下一个文件
     *
     * @param fileInputStream
     * @return
     * @throws IOException
     */
    private static FileBean getNextFile(FileInputStream fileInputStream) throws IOException {
        // 读取前四个字节，获取文件名长度
        byte[] byte4s = new byte[4];
        int len = fileInputStream.read(byte4s);
        if (len == -1) {
            return null;
        }

        int fileNameLen = FileUtils.byteArr2Int(byte4s);

        // 读取文件名长度，组装成文件名
        byte[] fileNameBytes = new byte[fileNameLen];
        fileInputStream.read(fileNameBytes);
        String fileName = new String(fileNameBytes);

        // 读取四个字节，获取文件内容长度
        fileInputStream.read(byte4s);
        int fileContentLen = FileUtils.byteArr2Int(byte4s);
        // 读取文件内容长度，获取文件内容数组
        byte[] fileContentBytes = new byte[fileContentLen];
        fileInputStream.read(fileContentBytes);

        return new FileBean(fileName, fileContentBytes);
    }


}
