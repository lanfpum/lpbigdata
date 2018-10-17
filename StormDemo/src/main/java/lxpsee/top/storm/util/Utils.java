package lxpsee.top.storm.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/12 16:13.
 */
public class Utils {
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getPID() {
        return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    }

    public static String getTID() {
        return Thread.currentThread().getName();
    }

    public static String getOID(Object object) {
        return object.getClass().getSimpleName() + "@" + object.hashCode();
    }

    public static String getInfo(Object o, String msg) {
        return "主机名：" + getHostName() + ",进程ID:" + getPID() + "，线程ID:" + getTID() + "，对象信息----" + getOID(o) + ",附带信息：" + msg;
    }

    public static String getLocalInfo(Object o, String msg) {
        return getHostName() + ",PID:" + getPID() + ",Thread ID:" + getTID() + ",OBJ ----" + getOID(o) + ",msg:" + msg;
    }

    public static void sendToClient(Object o, String msg, int port) {
        try {
            OutputStream outputStream = new Socket("ip201", port).getOutputStream();
            outputStream.write((getInfo(o, msg) + "\r\n").getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendToLocalhost(Object o, String msg) {
        try {
            OutputStream outputStream = new Socket("localhost", 8888).getOutputStream();
            outputStream.write((getLocalInfo(o, msg) + "\r\n").getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
