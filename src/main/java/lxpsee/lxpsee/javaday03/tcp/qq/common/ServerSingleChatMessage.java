package lxpsee.lxpsee.javaday03.tcp.qq.common;

/**
 * 服务器端私聊消息
 */
public class ServerSingleChatMessage extends Message {
    private byte[] senderInfoBytes;
    private byte[] receverInfoBytes;
    private byte[] messageBytes;

    public byte[] getReceverInfoBytes() {
        return receverInfoBytes;
    }

    public void setReceverInfoBytes(byte[] receverInfoBytes) {
        this.receverInfoBytes = receverInfoBytes;
    }

    public byte[] getSenderInfoBytes() {
        return senderInfoBytes;
    }

    public void setSenderInfoBytes(byte[] senderInfoBytes) {
        this.senderInfoBytes = senderInfoBytes;
    }

    public byte[] getMessageBytes() {
        return messageBytes;
    }

    public void setMessageBytes(byte[] messageBytes) {
        this.messageBytes = messageBytes;
    }

    @Override
    public int getMessageType() {
        return SERVER_TO_CLIENT_SINGLE_CHAT;
    }
}
