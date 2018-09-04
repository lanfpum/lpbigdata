package lxpsee.lxpsee.javaday03.tcp.qq.server;

import top.lxpsee.javaday03.tcp.qq.common.Message;
import top.lxpsee.javaday03.tcp.qq.common.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * 努力常态化  2018/7/9 9:42 The world always makes way for the dreamer
 */
public class QQServer {
    private static QQServer qqServer = new QQServer();

    private QQServer() {
    }

    public static QQServer getInstence() {
        return qqServer;
    }

    /**
     * 维护所有socket连接，对象
     * key:remoteIP + ":" + remotePort
     */
    private Map<String, Socket> allSockets = new HashMap<String, Socket>();

    /**
     * 启动服务器
     */
    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);

            while (true) {
                // 方法是阻塞的
                Socket socket = serverSocket.accept();
                InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
                // 远程ip和远程端口
                String remoteIp = remoteSocketAddress.getAddress().getHostAddress();
                int remotePort = remoteSocketAddress.getPort();
                String key = remoteIp + ":" + remotePort;
                allSockets.put(key, socket);
                // 开起服务器端通信线程
                new CommonThread(socket).start();
                this.broadcastfriends();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 广播好友列表
     */
    public void broadcastfriends() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(Utils.int2Bytes(Message.SERVER_TO_CLIENT_REFRESH_FRIENTS));
            byteArrayOutputStream.write(Utils.int2Bytes(QQServer.getInstence().getFriendBytes().length));
            byteArrayOutputStream.write(QQServer.getInstence().getFriendBytes());
            byteArrayOutputStream.close();

            QQServer.getInstence().broadcast(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取好友列表方法
     */
    public byte[] getFriendBytes() {
        List<String> friendList = new ArrayList<String>(allSockets.keySet());
        return Utils.serializableObject((Serializable) friendList);
    }

    /**
     * 广播
     */
    public void broadcast(byte[] bytes) {
        Iterator<Socket> iterator = allSockets.values().iterator();

        while (iterator.hasNext()) {
            try {
                OutputStream outputStream = iterator.next().getOutputStream();
                outputStream.write(bytes);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 私聊
     */
    public void send(byte[] bytes, byte[] userInfo) {
        try {
            String key = new String(userInfo);
            OutputStream outputStream = allSockets.get(key).getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定用户
     */
    public synchronized void removeUser(String user) {
        allSockets.remove(user);
    }
}
