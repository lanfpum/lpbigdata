package lxpsee.lxpsee.javaday01.housework.monkandpool;

/**
 * 努力常态化  2018/6/28 14:52 The world always makes way for the dreamer
 */
public class App {
    public static void main(String[] args) {
        Pool pool = Pool.getInstance();

        for (int i = 0; i < 30; i++) {
            new Monk(pool, "monk" + i).start();
        }
    }

}
