package lxpsee.lxpsee.javaday01.housework.beeandbear;

/**
 * 努力常态化  2018/6/27 15:32 The world always makes way for the dreamer
 * <p>
 * 熊吃蜂蜜问题：
 * 2只熊，100只蜜蜂，
 * 蜜蜂每次生成的蜂蜜量为1，罐子的容量是100，当罐子的蜂蜜量达到20时，熊就吃光。
 * 罐子使用单例设计模式实现。
 */
public class App {
    public static void main(String[] args) {
        Pot pot = Pot.getPot();

        for (int i = 0; i < 100; i++) {
            new Bee("bee" + i + "号蜜蜂", pot).start();
        }

        for (int i = 0; i < 2; i++) {
            new Bear("熊" + i, pot).start();
        }
    }

}
