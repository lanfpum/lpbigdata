package lxpsee.lxpsee.javaday05.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/16 17:39.
 */

public class TestFileCopy {

    /**
     * 1.从流中获取通道
     * 2.从ByteBuffer获取缓冲区
     * 3.写之前拍板，之后清空缓冲区
     * 4.关闭通道
     */
    @Test
    public void testFileCopy() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("D:\\pic\\0.jpg");
        FileChannel fileChannel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\pic\\2.jpg");
        FileChannel channel = fileOutputStream.getChannel();

        ByteBuffer byteByffer = ByteBuffer.allocate(1024 * 8);

        while (fileChannel.read(byteByffer) != -1) {
            byteByffer.flip();
            channel.write(byteByffer);
            byteByffer.clear();
        }

        channel.close();
        fileChannel.close();
    }

}
