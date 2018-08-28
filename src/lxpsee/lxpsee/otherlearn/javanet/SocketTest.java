package lxpsee.lxpsee.otherlearn.javanet;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/1 22:53.
 * <p>
 * 核心技术 2 网络3-1
 * 报错 java.net.UnknownHostException: time-A.timefreq.bldrdoc.gov，无网络
 */
public class SocketTest {
    public static void main(String[] args) {
        try (Socket socket = new Socket("time-A.timefreq.bldrdoc.gov", 13)) {
            InputStream inputStream = socket.getInputStream();
            Scanner scanner = new Scanner(inputStream);

            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
