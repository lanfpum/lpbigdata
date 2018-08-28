package lxpsee.lxpsee.javaday03.udp.screenbroadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * 努力常态化  2018/7/4 17:34 The world always makes way for the dreamer
 * <p>
 * 屏广发送器
 */
public class ScreenBroadCastSender {

    private DatagramSocket datagramSocket;
    private InetSocketAddress inetSocketAddress;

    public ScreenBroadCastSender() {
        try {
            datagramSocket = new DatagramSocket(8888);
            inetSocketAddress = new InetSocketAddress("192.168.31.37", 9999);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送unit列表
     */
    public void send(List<FrameUnit> frameUnitList) {
        try {
            for (FrameUnit frameUnit : frameUnitList) {
                byte[] frameUnitBytes = popFrameUnit(frameUnit);
                DatagramPacket datagramPacket = new DatagramPacket(frameUnitBytes, frameUnitBytes.length);
                datagramPacket.setSocketAddress(inetSocketAddress);
                datagramSocket.send(datagramPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将每个frameUnit组装成byte[] 8 + 1 + 1 + 4 + n
     */
    private byte[] popFrameUnit(FrameUnit frameUnit) {
        byte[] unitBytes = new byte[frameUnit.getDataLen() + 14];
        byte[] frameUnitIdBytes = ScreenBroadCastUtils.long2Bytes(frameUnit.getFrameId());
        System.arraycopy(frameUnitIdBytes, 0, unitBytes, 0, 8);

        unitBytes[8] = (byte) frameUnit.getFrameUnitCount();
        unitBytes[9] = (byte) frameUnit.getFrameUnitNo();
        int dataLen = frameUnit.getDataLen();
        System.arraycopy(ScreenBroadCastUtils.int2Bytes(dataLen), 0, unitBytes, 10, 4);

        System.arraycopy(frameUnit.getDataBytes(), 0, unitBytes, 14, dataLen);
        return unitBytes;
    }
}
