package lxpsee.lxpsee.javaday01.threadtest.saleticket;

/**
 * 努力常态化  2018/6/26 15:31 The world always makes way for the dreamer
 */
public class Saler extends Thread {
    private String name;
    private TicketPool ticketPool;

    public Saler(String name, TicketPool ticketPool) {
        this.name = name;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (true) {
            int ticket = ticketPool.getTicket();
            if (ticket == 0) {
                return;
            }

            System.out.println(name + " : " + ticket);
        }
    }
}
