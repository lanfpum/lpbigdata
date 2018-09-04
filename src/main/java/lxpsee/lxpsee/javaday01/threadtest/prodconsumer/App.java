package lxpsee.lxpsee.javaday01.threadtest.prodconsumer;

/**
 * 努力常态化  2018/6/27 9:01 The world always makes way for the dreamer
 */
public class App {
    public static void main(String[] args) {
        TicketPool ticketPool = new TicketPool();
        new Producer("Tom", ticketPool).start();
        new Producer("Jim", ticketPool).start();
        new Producer("Air", ticketPool).start();
        new Consumer("df", ticketPool).start();
    }

}
