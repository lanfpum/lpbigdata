package lxpsee.lxpsee.javaday03.tcp.qq.common;

/**
 * 努力常态化  2018/7/9 11:57 The world always makes way for the dreamer
 * <p>
 * 客户端退出消息
 */
public class ClientExitMessage extends Message {

    @Override
    public int getMessageType() {
        return Message.CLIENT_TO_SERVER_EXIT;
    }
}
