package lxpsee.lxpsee.javaday01.threadtest.prodconsumer;

/**
 * 努力常态化  2018/6/27 8:53 The world always makes way for the dreamer
 */
public class Producer extends Thread {
    private static int        index = 0;
    private        String     name;
    private        TicketPool ticketPool;

    public Producer(String name, TicketPool ticketPool) {
        this.name = name;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (true) {
            ticketPool.add(index++);
            System.out.println(name + " : " + index);
        }
    }
}
