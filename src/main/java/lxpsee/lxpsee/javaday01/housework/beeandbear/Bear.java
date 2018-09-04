package lxpsee.lxpsee.javaday01.housework.beeandbear;

/**
 * 努力常态化  2018/6/27 15:31 The world always makes way for the dreamer
 * 熊线程
 */
public class Bear extends Thread {
    private String name;
    private Pot    pot;

    public Bear(String name, Pot pot) {
        this.name = name;
        this.pot = pot;
    }

    @Override
    public void run() {
        while (true) {
            pot.remove(name);
        }
    }
}
