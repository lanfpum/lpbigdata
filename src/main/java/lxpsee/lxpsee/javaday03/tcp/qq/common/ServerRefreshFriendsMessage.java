package lxpsee.lxpsee.javaday03.tcp.qq.common;

/**
 * 刷新好友列表信息
 * <p>
 * 好友列表数据
 */
public class ServerRefreshFriendsMessage extends Message {
    private byte[] friendsListBytes;

    public byte[] getFriendsListBytes() {
        return friendsListBytes;
    }

    public void setFriendsListBytes(byte[] friendsListBytes) {
        this.friendsListBytes = friendsListBytes;
    }

    @Override
    public int getMessageType() {
        return SERVER_TO_CLIENT_REFRESH_FRIENTS;
    }
}
