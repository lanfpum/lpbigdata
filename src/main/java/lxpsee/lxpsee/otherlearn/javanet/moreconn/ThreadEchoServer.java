package lxpsee.lxpsee.otherlearn.javanet.moreconn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/1 23:48.
 */

public class ThreadEchoServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            int i = 1;
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("spawnig" + i);
                ThreadEchoHandler threadEchoHandler = new ThreadEchoHandler(socket);
                Thread thread = new Thread(threadEchoHandler);
                thread.start();
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
