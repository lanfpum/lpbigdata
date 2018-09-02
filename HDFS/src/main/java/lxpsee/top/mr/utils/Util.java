package lxpsee.top.mr.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/8/16 09:22.
 */
public class Util {

    public static String getInfo(Object object, String msg) {
        return "主机： " + getHostName() + " ，进程： " + getPID() + " , 线程： " + getTID()
                + " , 类属性： " + getOName(object) + " --- " + msg;
    }

    /**
     * 得到主机名
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 得到进程ID
     */
    public static int getPID() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return Integer.parseInt(name.substring(0, name.indexOf("@")));
    }

    /**
     * 得到线程ID
     */
    public static String getTID() {
        return Thread.currentThread().getName();
    }

    /**
     * 得到对象类名和对象的哈希编码
     */
    public static String getOName(Object o) {
        String simpleName = o.getClass().getSimpleName();
        return simpleName + "@" + o.hashCode();
    }
}
