package lxpsee.lxpsee.javaday03.tcp.qq.common;

import top.lxpsee.javaday03.tcp.qq.server.QQServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 努力常态化  2018/7/9 10:18 The world always makes way for the dreamer
 * <p>
 * 消息工厂
 */
public class MessageFactory {

    /**
     * 从流中解析消息 ,解析客户端消息，并直接转换成服务器消息
     */
    public static byte[] parseClientMessageAndSend(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] msgTypeBytes = new byte[4];
        inputStream.read(msgTypeBytes);
        Message message;

        switch (Utils.bytes2Int(msgTypeBytes)) {
            /* 1.客户端群聊消息
             *      客户端消息：消息长度和消息内容
             *      服务端消息：发送者和消息内容
             *
             *    1.1:从socket中获取输入流，转换成客户端消息
             *    1.2:将客户端消息转换成服务器消息
             *    1.3:将服务器端消息写入内存输出流，广播发送(4 + 4 + n + 4 + n)
             */
            case Message.CLIENT_TO_SERVER_CHATS: {
                message = new ClientChatsMessage();
                byte[] messageLen = new byte[4];
                inputStream.read(messageLen);
                byte[] messageBytes = new byte[Utils.bytes2Int(messageLen)];
                inputStream.read(messageBytes);
                ((ClientChatsMessage) message).setMessageBytes(messageBytes);

                ServerChatsMessage serverMessage = new ServerChatsMessage();
                serverMessage.setSenderInfoBytes(Utils.getUserInfo(socket));
                serverMessage.setMessageBytes(((ClientChatsMessage) message).getMessageBytes());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(Utils.int2Bytes(Message.SERVER_TO_CLIENT_CHATS));
                byteArrayOutputStream.write(Utils.int2Bytes(serverMessage.getSenderInfoBytes().length));
                byteArrayOutputStream.write(serverMessage.getSenderInfoBytes());
                byteArrayOutputStream.write(Utils.int2Bytes(serverMessage.getMessageBytes().length));
                byteArrayOutputStream.write(serverMessage.getMessageBytes());
                byteArrayOutputStream.close();

                QQServer.getInstence().broadcast(byteArrayOutputStream.toByteArray());
            }
            break;

            /* 2.客户端刷新好友列表消息,给指定的客户端刷新数据 */
            case Message.CLIENT_TO_SERVER_REFRESH_FRIENDS: {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(Utils.int2Bytes(Message.SERVER_TO_CLIENT_REFRESH_FRIENTS));
                byteArrayOutputStream.write(Utils.int2Bytes(QQServer.getInstence().getFriendBytes().length));
                byteArrayOutputStream.write(QQServer.getInstence().getFriendBytes());
                byteArrayOutputStream.close();

                QQServer.getInstence().send(byteArrayOutputStream.toByteArray(), Utils.getUserInfo(socket));
            }
            break;

            /* 3.客户端退出,广播刷新好友列表 */
            case Message.CLIENT_TO_SERVER_EXIT: {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(Utils.int2Bytes(Message.SERVER_TO_CLIENT_REFRESH_FRIENTS));
                byteArrayOutputStream.write(Utils.int2Bytes(QQServer.getInstence().getFriendBytes().length));
                byteArrayOutputStream.write(QQServer.getInstence().getFriendBytes());
                byteArrayOutputStream.close();

                QQServer.getInstence().broadcast(byteArrayOutputStream.toByteArray());
            }
            break;

            /* 4.客户端私聊
             *       客户端信息：接受者信息和信息内容
             *       服务端信息：发送者信息，接受者信息，信息内容
             *   4.1：从socket输入流中读取数据成客户端信息
             *   4.2：将客户端信息解析成服务端信息
             *   4.3：发送给指定socket
             */
            case Message.CLIENT_TO_SERVER_SINGLE_CHAT: {
                /* 创建消息对象，读取消息长度，读取消息内容，设置消息数组 4 -> 发送者信息 -> 4 -> 信息内容*/
                message = new ClientSingleChatMessage();
                byte[] bytes = new byte[4];

                inputStream.read(bytes);
                byte[] receverInfoBytes = new byte[Utils.bytes2Int(bytes)];
                inputStream.read(receverInfoBytes);
                ((ClientSingleChatMessage) message).setReceverInfoBytes(receverInfoBytes);

                inputStream.read(bytes);
                byte[] messageBytes = new byte[Utils.bytes2Int(bytes)];
                inputStream.read(messageBytes);
                ((ClientSingleChatMessage) message).setMessageBytes(messageBytes);

                ServerSingleChatMessage serverMessage = new ServerSingleChatMessage();
                serverMessage.setSenderInfoBytes(Utils.getUserInfo(socket));
                serverMessage.setReceverInfoBytes(((ClientSingleChatMessage) message).getReceverInfoBytes());
                serverMessage.setMessageBytes(((ClientSingleChatMessage) message).getMessageBytes());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(Message.SERVER_TO_CLIENT_SINGLE_CHAT);
                byteArrayOutputStream.write(Utils.int2Bytes(serverMessage.getSenderInfoBytes().length));
                byteArrayOutputStream.write(serverMessage.getSenderInfoBytes());
                byteArrayOutputStream.write(Utils.int2Bytes(serverMessage.getMessageBytes().length));
                byteArrayOutputStream.write(serverMessage.getMessageBytes());
                byteArrayOutputStream.close();

                QQServer.getInstence().send(byteArrayOutputStream.toByteArray(), serverMessage.getReceverInfoBytes());
            }
            break;
        }

        return null;
    }

    /**
     * 解析服务器消息
     */
    public static Message parseServerMessage(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] messageType = new byte[4];
        inputStream.read(messageType);

        switch (Utils.bytes2Int(messageType)) {
            /* 刷新好友列表：从socket中读取，转换成服务器信息 */
            case Message.SERVER_TO_CLIENT_REFRESH_FRIENTS: {
                byte[] bytes = new byte[4];
                inputStream.read(bytes);
                int friendsLen = Utils.bytes2Int(bytes);
                byte[] friendsBytes = new byte[friendsLen];
                inputStream.read(friendsBytes);

                ServerRefreshFriendsMessage serverRefreshFriendsMessage = new ServerRefreshFriendsMessage();
                serverRefreshFriendsMessage.setFriendsListBytes(friendsBytes);
                return serverRefreshFriendsMessage;
            }

            /* 私聊： 从socket中读取，转换成服务器信息 */
            case Message.SERVER_TO_CLIENT_SINGLE_CHAT: {
                byte[] bytes4 = new byte[4];

                inputStream.read(bytes4);
                int senderInfoLen = Utils.bytes2Int(bytes4);
                byte[] senderInfoBytes = new byte[senderInfoLen];
                inputStream.read(senderInfoBytes);

                inputStream.read(bytes4);
                int messageLen = Utils.bytes2Int(bytes4);
                byte[] messageBytes = new byte[messageLen];
                inputStream.read(messageBytes);

                ServerSingleChatMessage serverSingleChatMessage = new ServerSingleChatMessage();
                serverSingleChatMessage.setSenderInfoBytes(senderInfoBytes);
                serverSingleChatMessage.setMessageBytes(messageBytes);
                return serverSingleChatMessage;
            }

            /* 群聊 */
            case Message.SERVER_TO_CLIENT_CHATS: {
                byte[] byte4s = new byte[4];

                inputStream.read(byte4s);
                int senderInfoLen = Utils.bytes2Int(byte4s);
                byte[] senderInfoBytes = new byte[senderInfoLen];
                inputStream.read(senderInfoBytes);

                inputStream.read(byte4s);
                int messageLen = Utils.bytes2Int(byte4s);
                byte[] messageBytes = new byte[messageLen];
                inputStream.read(messageBytes);

                ServerChatsMessage serverChatsMessage = new ServerChatsMessage();
                serverChatsMessage.setSenderInfoBytes(senderInfoBytes);
                serverChatsMessage.setMessageBytes(messageBytes);
                return serverChatsMessage;
            }
        }

        return null;
    }

    /**
     * 组装客户端群聊消息,将输入框中的内容拼接成字节数组
     */
    public static byte[] popClientChatsMessage(String message) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(Utils.int2Bytes(Message.CLIENT_TO_SERVER_CHATS));

            byte[] bytes = message.getBytes();
            byteArrayOutputStream.write(Utils.int2Bytes(bytes.length));
            byteArrayOutputStream.write(bytes);

            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 组装私聊消息
     */
    public static byte[] popClientSingleChatMessage(String recvInfo, String messageText) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(Utils.int2Bytes(Message.CLIENT_TO_SERVER_SINGLE_CHAT));

            byte[] recvInfoBytes = recvInfo.getBytes();
            byteArrayOutputStream.write(Utils.int2Bytes(recvInfoBytes.length));
            byteArrayOutputStream.write(recvInfoBytes);

            byte[] messageTextBytes = messageText.getBytes();
            byteArrayOutputStream.write(Utils.int2Bytes(messageTextBytes.length));
            byteArrayOutputStream.write(messageTextBytes);

            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 组装刷新好友信息
     */
    public static byte[] popClientRefreshFriendsMessage() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(Utils.int2Bytes(Message.CLIENT_TO_SERVER_REFRESH_FRIENDS));
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
