package lxpsee.lxpsee.javaday01.housework.monkandpool;

/**
 * 努力常态化  2018/6/28 12:13 The world always makes way for the dreamer
 * 馒头池
 */
public class Pool {
    private        int  count      = 100;
    private static int  unEatMonks = 30;
    private static Pool pool;

    private Pool() {

    }

    public static Pool getInstance() {
        if (pool != null) {
            return pool;
        }

        synchronized (Pool.class) {
            if (pool == null) {
                pool = new Pool();
            }
            return pool;
        }
    }


    public int give(Monk monk) {
        //1.是否还有可吃馒头
        if (count == 0) {
            return 0;
        }

        //2.和尚是否吃饱了
        if (monk.getCount() == Monk.Max) {
            return 0;
        }

        //3.还有多余的馒头,和尚是否是第一次吃馒头
        if (count > unEatMonks) {
            int temp = count;
            count--;

            if (monk.getCount() == 0) {
                unEatMonks--;
            }

            return temp;
        } else {
            if (monk.getCount() == 0) {
                int temp = count;
                count--;
                unEatMonks--;
                return temp;
            }
        }

        return 0;
    }
}
