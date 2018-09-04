package lxpsee.lxpsee.javaday01.threadtest.saleticket;

/**
 * 努力常态化  2018/6/26 15:34 The world always makes way for the dreamer
 */
public class App {
    public static void main(String[] args) {
        TicketPool ticketPool = new TicketPool();
        Saler saler1 = new Saler("bob", ticketPool);
        Saler saler2 = new Saler("tom", ticketPool);
        saler1.start();
        saler2.start();
    }

}
