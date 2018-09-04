package lxpsee.lxpsee.javaday03.tcp.qq.common;

/**
 * 努力常态化  2018/7/9 10:19 The world always makes way for the dreamer
 * <p>
 * 消息的抽象类
 */
public abstract class Message {
    /* 客户端发送给服务器的消息 */
    public static final int CLIENT_TO_SERVER_SINGLE_CHAT     = 0;
    public static final int CLIENT_TO_SERVER_CHATS           = 1;
    public static final int CLIENT_TO_SERVER_REFRESH_FRIENDS = 2;
    public static final int CLIENT_TO_SERVER_EXIT            = 3;

    /* 服务器发送给客户端的消息 */
    public static final int SERVER_TO_CLIENT_REFRESH_FRIENTS = 11;
    public static final int SERVER_TO_CLIENT_SINGLE_CHAT     = 12;
    public static final int SERVER_TO_CLIENT_CHATS           = 13;

    /**
     * 获取消息类型
     */
    public abstract int getMessageType();
}
