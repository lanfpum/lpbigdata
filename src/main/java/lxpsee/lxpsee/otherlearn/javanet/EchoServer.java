package lxpsee.lxpsee.otherlearn.javanet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/1 23:26.
 * <p>
 * 核心技术 2 网络3-3
 * socket 的服务端
 */

public class EchoServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8189);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        Scanner scanner = new Scanner(inputStream);
        PrintWriter printWriter = new PrintWriter(outputStream, true /* autoFlush */);
        printWriter.println("Hello , Enter BYE to exit.");

        boolean done = false;

        while (!done && scanner.hasNextLine()) {
            String line = scanner.nextLine();
            printWriter.println("Echo :" + line);

            if (line.trim().equals("BYE")) {
                done = true;
            }
        }
    }
}
