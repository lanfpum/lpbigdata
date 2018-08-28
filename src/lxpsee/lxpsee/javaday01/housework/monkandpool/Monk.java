package lxpsee.lxpsee.javaday01.housework.monkandpool;

/**
 * 努力常态化  2018/6/28 12:12 The world always makes way for the dreamer
 * 和尚
 */
public class Monk extends Thread {

    private Pool pool;
    private String name;

    public static int Max = 5;

    // 和尚吃馒头的数量
    private int count;

    public Monk(Pool pool, String name) {
        this.pool = pool;
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void run() {
        while (true) {
            int res = pool.give(this);

            if (res != 0) {
                count++;
            } else {
                break;
            }
            yield();
            System.out.println(name + " : " + count);
        }
    }
}
