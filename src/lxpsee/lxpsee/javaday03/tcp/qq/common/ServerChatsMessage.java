package lxpsee.lxpsee.javaday03.tcp.qq.common;

/**
 * 服务器端群聊消息
 * <p>
 * 报文设计：前4个字节为用户信息长度-->>用户信息 -->> 4个字节为发送的信息长度 -->> 发送的信息
 */
public class ServerChatsMessage extends Message {

    private byte[] senderInfoBytes;
    private byte[] messageBytes;

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
        return SERVER_TO_CLIENT_CHATS;
    }
}
