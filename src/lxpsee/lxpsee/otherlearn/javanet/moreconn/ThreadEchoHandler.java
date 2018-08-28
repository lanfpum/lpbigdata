package lxpsee.lxpsee.otherlearn.javanet.moreconn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/1 23:42.
 */

public class ThreadEchoHandler implements Runnable {
    private Socket socket;

    public ThreadEchoHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Scanner scanner = new Scanner(inputStream);
            PrintWriter printWriter = new PrintWriter(outputStream, true /* autoFlush */);
            printWriter.println("Hello, Enter BYE to exit.");
            boolean done = false;

            while (!done && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                printWriter.println("Echo : " + line);

                if (line.trim().equals("BYE")) {
                    done = true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
