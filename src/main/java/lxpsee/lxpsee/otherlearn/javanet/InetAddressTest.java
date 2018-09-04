package lxpsee.lxpsee.otherlearn.javanet;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/1 23:13.
 * <p>
 * 核心技术 2 网络3-2
 */
public class InetAddressTest {
    public static void main(String[] args) throws UnknownHostException {
        if (args.length > 0) {
            String host = args[0];
            InetAddress[] addresses = InetAddress.getAllByName(host);

            for (InetAddress address : addresses) {
                System.out.println(address);
            }

        } else {
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println(localHost);
        }
    }
}
