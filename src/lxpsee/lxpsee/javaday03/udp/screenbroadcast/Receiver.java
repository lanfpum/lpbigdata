package lxpsee.lxpsee.javaday03.udp.screenbroadcast;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * 努力常态化  2018/7/4 22:32 The world always makes way for the dreamer
 */
public class Receiver extends Thread {
    private DatagramSocket socket;
    private DatagramPacket packet;
    private StudentUI ui;

    private byte[] bufBytes = new byte[Teacher.FRAME_UNIT_MAX + 14];
    private Map<Integer, FrameUnit> unitMap = new HashMap<Integer, FrameUnit>();

    public Receiver(StudentUI ui) {
        try {
            this.ui = ui;
            socket = new DatagramSocket(8888);
            packet = new DatagramPacket(bufBytes, bufBytes.length);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            long currentFrameId = 0;

            while (true) {
                socket.receive(packet);
                int length = packet.getLength();
                FrameUnit frameUnit = convertData2FrameUnit(bufBytes, 0, length);
                int frameUnitNo = frameUnit.getFrameUnitNo();
                long newFrameId = frameUnit.getFrameId();

                if (newFrameId == currentFrameId) {
                    unitMap.put(frameUnitNo, frameUnit);
                } else if (newFrameId > currentFrameId) {
                    currentFrameId = newFrameId;
                    unitMap.clear();
                    unitMap.put(frameUnitNo, frameUnit);
                }

                processFrame();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理整个unit集合,注意解压
     */
    private void processFrame() {
        try {
            int frameUnitCount = unitMap.values().iterator().next().getFrameUnitCount();

            if (frameUnitCount == unitMap.size()) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                for (int i = 0; i < frameUnitCount; i++) {
                    FrameUnit frameUnit = unitMap.get(i);
                    byteArrayOutputStream.write(frameUnit.getDataBytes());
                }

                unitMap.clear();
                ui.updateIcon(ScreenBroadCastUtils.unZip(byteArrayOutputStream.toByteArray()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 将接受到的数据转换成帧单元
     */
    private FrameUnit convertData2FrameUnit(byte[] bufBytes, int i, int length) {
        FrameUnit frameUnit = new FrameUnit();
        frameUnit.setFrameId(ScreenBroadCastUtils.bytes2Long(bufBytes));
        frameUnit.setFrameUnitCount(bufBytes[8]);
        frameUnit.setFrameUnitNo(bufBytes[9]);
        int dataLen = ScreenBroadCastUtils.bytesWithOffset2Int(bufBytes, 10);
        frameUnit.setDataLen(dataLen);

        byte[] dataBytes = new byte[dataLen];
        System.arraycopy(bufBytes, 14, dataBytes, 0, dataLen);
        frameUnit.setDataBytes(dataBytes);
        return frameUnit;
    }
}
