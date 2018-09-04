package lxpsee.lxpsee.javaday01.threadtest.prodconsumer;

/**
 * 努力常态化  2018/6/27 8:58 The world always makes way for the dreamer
 */
public class Consumer extends Thread {
    private String     name;
    private TicketPool ticketPool;

    public Consumer(String name, TicketPool ticketPool) {
        this.name = name;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (true) {
            int remove = ticketPool.remove();
            System.out.println(name + " : " + remove);
        }
    }
}
