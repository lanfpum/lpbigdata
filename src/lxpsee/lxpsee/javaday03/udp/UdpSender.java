package lxpsee.lxpsee.javaday03.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * 努力常态化  2018/6/28 15:19 The world always makes way for the dreamer
 * 发送方
 */
public class UdpSender {
    public static void main(String[] args) throws Exception {
        DatagramSocket datagramSocket = new DatagramSocket(9999);
        int i = 0;

        while (true) {
            byte[] bytes = ("Hello world" + i++).getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
            datagramPacket.setSocketAddress(new InetSocketAddress("localhost", 8888));
            datagramSocket.send(datagramPacket);

            Thread.sleep(1000);
        }

    }
}
