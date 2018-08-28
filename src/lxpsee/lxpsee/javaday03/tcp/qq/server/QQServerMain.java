package lxpsee.lxpsee.javaday03.tcp.qq.server;

/**
 * 努力常态化  2018/7/9 9:38 The world always makes way for the dreamer
 */
public class QQServerMain {
    public static void main(String[] args) {
        System.out.println("服务器端启动了.....");
        QQServer.getInstence().start();
    }

}
