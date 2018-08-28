package lxpsee.lxpsee.javaday03.tcp.qq.common;

/**
 * 努力常态化  2018/7/9 11:27 The world always makes way for the dreamer
 * <p>
 * 客户端单聊信息
 */
public class ClientSingleChatMessage extends Message {

    private byte[] receverInfoBytes;
    private byte[] messageBytes;

    public byte[] getReceverInfoBytes() {
        return receverInfoBytes;
    }

    public void setReceverInfoBytes(byte[] receverInfoBytes) {
        this.receverInfoBytes = receverInfoBytes;
    }

    public byte[] getMessageBytes() {
        return messageBytes;
    }

    public void setMessageBytes(byte[] messageBytes) {
        this.messageBytes = messageBytes;
    }

    @Override
    public int getMessageType() {
        return Message.CLIENT_TO_SERVER_SINGLE_CHAT;
    }
}
