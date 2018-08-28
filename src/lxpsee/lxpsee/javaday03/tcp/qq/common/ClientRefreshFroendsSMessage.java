package lxpsee.lxpsee.javaday03.tcp.qq.common;

/**
 * 努力常态化  2018/7/9 11:55 The world always makes way for the dreamer
 * <p>
 * 客户端刷新好友列表消息
 */
public class ClientRefreshFroendsSMessage extends Message {

    @Override
    public int getMessageType() {
        return CLIENT_TO_SERVER_REFRESH_FRIENDS;
    }
}
