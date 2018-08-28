package lxpsee.lxpsee.javaday01.housework.beeandbear;

/**
 * 努力常态化  2018/6/27 15:28 The world always makes way for the dreamer
 * 蜜蜂线程
 */
public class Bee extends Thread {

    private String name;
    private Pot pot;

    public Bee(String name, Pot pot) {
        this.name = name;
        this.pot = pot;
    }

    @Override
    public void run() {
        while (true) {
            pot.add(1, name);
            /*
             *蜜蜂线程太多，总有更大的概率抢到罐子，所以同等条件下熊多数时候是在罐子灌满时才能吃到蜂蜜，
             *为了让熊尽可能在多种情况下都能吃到，我们就让蜜蜂每次放完蜂蜜后时候睡一下
             */
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
