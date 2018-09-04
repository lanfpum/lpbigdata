package lxpsee.lxpsee.javaday03.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 努力常态化  2018/6/28 15:39 The world always makes way for the dreamer
 * 接收方
 */
public class UdpReciver {
    public static void main(String[] args) throws Exception {
        DatagramSocket datagramSocket = new DatagramSocket(8888);

        while (true) {
            byte[] bytes = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
            datagramSocket.receive(datagramPacket);

            String str = new String(bytes, 0, datagramPacket.getLength());
            System.out.println(str);
        }

    }

}
