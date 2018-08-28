package lxpsee.lxpsee.javaday03.tcp.qq.common;

/**
 * 努力常态化  2018/7/9 11:48 The world always makes way for the dreamer
 * <p>
 * 客户端群聊消息
 */
public class ClientChatsMessage extends Message {
    private byte[] messageBytes;

    public byte[] getMessageBytes() {
        return messageBytes;
    }

    public void setMessageBytes(byte[] messageBytes) {
        this.messageBytes = messageBytes;
    }

    @Override
    public int getMessageType() {
        return Message.CLIENT_TO_SERVER_CHATS;
    }
}
