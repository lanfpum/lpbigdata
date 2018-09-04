package lxpsee.lxpsee.javaday03.tcp.qq.client;

import top.lxpsee.javaday03.tcp.qq.common.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

/**
 * 接收信息线程
 * <p>
 * instanceof 可以是多态，.class更精确
 */
public class ClientCommThread extends Thread {

    private QQClientUI ui;
    private Socket     socket;

    public ClientCommThread(Socket socket, QQClientUI ui) {
        this.socket = socket;
        this.ui = ui;

        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = MessageFactory.parseServerMessage(socket);
                System.out.println(message.getClass() + " :" + message.getMessageType());

                /* 刷新好友列表，取出好友列表，反串行化 */
                if (message.getClass() == ServerRefreshFriendsMessage.class) {
                    ServerRefreshFriendsMessage serverRefreshFriendsMessage = (ServerRefreshFriendsMessage) message;
                    byte[] friendsListBytes = serverRefreshFriendsMessage.getFriendsListBytes();
                    List<String> friendList = (List<String>) Utils.deSerializableObject(friendsListBytes);
                    ui.refreshFriends(friendList);

                } else if (message.getClass() == ServerSingleChatMessage.class) {
                    /* 私聊：1.接收私聊消息，获取发送者信息和信息内容
                     *      2.判断私聊中窗口是否已经打开，是就打开原来窗口，否则新开窗口,并放入窗口集合中
                     *      3.更新历史区记录
                     */
                    ServerSingleChatMessage serverSingleChatMessage = (ServerSingleChatMessage) message;
                    String senderInfo = new String(serverSingleChatMessage.getSenderInfoBytes());
                    String messageInfo = new String(serverSingleChatMessage.getMessageBytes());

                    QQClientChatSingleUI qqClientChatSingleUI;
                    if (ui.windows.containsKey(senderInfo)) {
                        qqClientChatSingleUI = ui.windows.get(senderInfo);
                        qqClientChatSingleUI.setVisible(true);
                    } else {
                        qqClientChatSingleUI = new QQClientChatSingleUI(this, senderInfo);
                        ui.windows.put(senderInfo, qqClientChatSingleUI);
                    }

                    qqClientChatSingleUI.updateHistory(senderInfo, messageInfo);

                } else if (message.getClass() == ServerChatsMessage.class) {
                    /* 群聊：接收群聊消息，获取发送者信息和信息内容，添加消息到历史区 */
                    ServerChatsMessage serverChatsMessage = (ServerChatsMessage) message;
                    String senderInfo = new String(serverChatsMessage.getSenderInfoBytes());
                    String messageInfo = new String(serverChatsMessage.getMessageBytes());
                    ui.addMsgToHistory(messageInfo, senderInfo);
                }
            } catch (IOException e) {
                return;
            }

        }
    }

    /**
     * 发送消息
     */
    public void sendMessage(byte[] msgBytes) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(msgBytes);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
