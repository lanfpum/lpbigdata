package lxpsee.lxpsee.javaday03.udp.screenbroadcast;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 努力常态化  2018/7/4 15:06 The world always makes way for the dreamer
 * 未指定到具体的文件会报错：java.io.FileNotFoundException: F:\bigdata\arch (拒绝访问。)
 */
public class TestCaptrue {

    /**
     * 不能直接将 screenCapture 直接转换成byte[],需要在网络上传输数据，串行化
     */
    @Test
    public void test() throws Exception {
        Robot robot = new Robot();
        BufferedImage screenCapture = robot.createScreenCapture(new Rectangle(0, 0, 1366, 768));
        ImageIO.write(screenCapture, "jpg", new FileOutputStream("F:\\bigdata\\arch\\1.jpg"));
    }

    @Test
    public void test2() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 0; i < 5; i++) {
            map.put(i, "s " + i);
        }

        Iterator<String> iterator = map.values().iterator();
        String next = iterator.next();
    }

}
