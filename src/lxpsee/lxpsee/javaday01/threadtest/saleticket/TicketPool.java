package lxpsee.lxpsee.javaday01.threadtest.saleticket;

/**
 * 努力常态化  2018/6/26 15:27 The world always makes way for the dreamer
 * 票池
 */
public class TicketPool {
    private Integer ticketNum = 100;

    public synchronized int getTicket() {
        int temp = ticketNum;
        if (temp == 0) {
            return 0;
        }

        ticketNum--;
        return temp;
    }

}
