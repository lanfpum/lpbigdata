package lxpsee.lxpsee.javaday03.tcp.qq.client;

import java.io.IOException;
import java.net.Socket;

/**
 * 设置对话框title，ui中也应该注入通信线程
 */
public class QQClientMain {
    public static void main(String[] args) throws IOException {
        QQClientUI qqClientUI = new QQClientUI();
        Socket socket = new Socket("192.168.31.37", 8888);

        String hostAddress = socket.getLocalAddress().getHostAddress();
        int localPort = socket.getLocalPort();
        qqClientUI.setTitle(hostAddress + " : " + localPort);

        ClientCommThread clientCommThread = new ClientCommThread(socket, qqClientUI);
        clientCommThread.start();

        qqClientUI.setClientCommThread(clientCommThread);
    }

}
