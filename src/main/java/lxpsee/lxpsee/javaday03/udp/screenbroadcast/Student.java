package lxpsee.lxpsee.javaday03.udp.screenbroadcast;

/**
 * udp的接收方
 * <p>
 * 努力常态化  2018/7/4 14:46 The world always makes way for the dreamer
 */
public class Student {
    public static void main(String[] args) {
        StudentUI ui = new StudentUI();
        new Receiver(ui).start();
    }

}
