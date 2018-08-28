package lxpsee.lxpsee.javaday01.threadtest.prodconsumer;

import java.util.LinkedList;

/**
 * 努力常态化  2018/6/27 8:43 The world always makes way for the dreamer
 * 票池
 */
public class TicketPool {

    private LinkedList<Integer> pool = new LinkedList<Integer>();

    public synchronized int add(int i) {
        pool.add(i);
        return i;
    }

    public synchronized int remove() {
        try {
            while (pool.isEmpty()) {
                Thread.sleep(50);
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return pool.removeFirst();
    }

}
