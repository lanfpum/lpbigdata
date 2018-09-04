package lxpsee.lxpsee.javaday03.udp.screenbroadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * udp的发送方
 * <p>
 * 努力常态化  2018/7/4 14:46 The world always makes way for the dreamer
 */
public class Teacher {

    private static DatagramSocket socket;

    static {
        try {
            socket = new DatagramSocket(9999);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }


    public final static int FRAME_UNIT_MAX = 60 * 1024;

    public static void main(String[] args) {
        while (true) {
            sendOneScreenData();
        }
    }

    /**
     * 发送一屏数据
     * 1.截图,压缩
     * 2.切图
     * 3.组装UdpPacket(8+1+1+4+n(max :60))
     * 4.发送
     */
    private static void sendOneScreenData() {
        byte[] frame = ScreenBroadCastUtils.captrueScreen();
        byte[] zipFrame = ScreenBroadCastUtils.zip(frame);
        List<FrameUnit> frameUnitList = splitFrame(zipFrame);
//        new ScreenBroadCastSender().send(frameUnitList);
        sendUnits(frameUnitList);
    }

    private static void sendUnits(List<FrameUnit> frameUnitList) {
        try {
            for (FrameUnit frameUnit : frameUnitList) {
                byte[] bytes = new byte[frameUnit.getDataLen() + 14];
                System.arraycopy(ScreenBroadCastUtils.long2Bytes(frameUnit.getFrameId()), 0, bytes, 0, 8);

                bytes[8] = (byte) frameUnit.getFrameUnitCount();
                bytes[9] = (byte) frameUnit.getFrameUnitNo();

                byte[] dataLen = ScreenBroadCastUtils.int2Bytes(frameUnit.getDataLen());
                System.arraycopy(dataLen, 0, bytes, 10, 4);
                System.arraycopy(frameUnit.getDataBytes(), 0, bytes, 14, dataLen.length);

                DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
                packet.setSocketAddress(new InetSocketAddress("192.168.31.255", 8888));
                socket.send(packet);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 切图,将屏幕截图切分成frameUnit集合
     * <p>
     * 注：1.判断是否被整除  2.最后一个的数据
     */
    private static List<FrameUnit> splitFrame(byte[] frame) {
        int count;
        boolean isDivision = (frame.length % FRAME_UNIT_MAX == 0);

        if (isDivision) {
            count = frame.length / FRAME_UNIT_MAX;
        } else {
            count = frame.length / FRAME_UNIT_MAX + 1;
        }

        FrameUnit frameUnit;
        long frameId = System.currentTimeMillis();
        List<FrameUnit> frameUnitList = new ArrayList<FrameUnit>();

        for (int i = 0; i < count; i++) {
            frameUnit = new FrameUnit();
            frameUnit.setFrameId(frameId);
            frameUnit.setFrameUnitCount(count);
            frameUnit.setFrameUnitNo(i);

            if (i == count - 1 && !isDivision) {
                int lastFrameUnitLen = frame.length % FRAME_UNIT_MAX;
                frameUnit.setDataLen(lastFrameUnitLen);
                byte[] unitDataBetys = new byte[lastFrameUnitLen];
                System.arraycopy(frame, i * FRAME_UNIT_MAX, unitDataBetys, 0, lastFrameUnitLen);
                frameUnit.setDataBytes(unitDataBetys);
            } else {
                frameUnit.setDataLen(FRAME_UNIT_MAX);
                byte[] unitDataBetys = new byte[FRAME_UNIT_MAX];
                System.arraycopy(frame, i * FRAME_UNIT_MAX, unitDataBetys, 0, FRAME_UNIT_MAX);
                frameUnit.setDataBytes(unitDataBetys);
            }

            frameUnitList.add(frameUnit);
        }

        return frameUnitList;
    }


}

