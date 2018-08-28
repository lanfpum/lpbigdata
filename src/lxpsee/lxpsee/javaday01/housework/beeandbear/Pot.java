package lxpsee.lxpsee.javaday01.housework.beeandbear;

/**
 * 努力常态化  2018/6/27 15:18 The world always makes way for the dreamer
 * 罐子类：单例设计模式（懒汉模式）
 */
public class Pot {
    // 拥有一个类类型的成员变量，罐内蜂蜜数量，罐子容积
    private static Pot pot;
    private Integer total;
    private int max = 100;

    // 私有构造函数
    private Pot() {
        total = 0;
    }

    // 对外提供相应的公有方法获得对象
    public static Pot getPot() {
        if (pot != null) {
            return pot;
        }

        synchronized (Pot.class) {
            if (pot == null) {
                pot = new Pot();
            }
            return pot;
        }
    }

    // 构造罐子添加蜂蜜的方法，为保证线程安全，采用synchronized同步代码块对add方法上锁
    synchronized void add(int n, String beeName) {
        while (total == max) {
            try {
                //当罐子灌满时，调用该方法的蜜蜂线程进入等待列表
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*如果将打印方法放在蜜蜂线程里，由于在多线程的状态下，在控制台打印的顺序并不能真正反映处理器处理的顺序，
         *为了更好的查看运行结果，所以我暂且选择放在了同步代码块里。
         *但其实在实际应用时，为提高程序运行速度，应该尽量减少同步代码块里的执行动作*/
        total += n;
        total += n;
        System.out.println(beeName + "加了一滴，罐子里有" + total + "滴蜂蜜");
        this.notifyAll();
    }

    //构造罐子的移除方法，同样为了保证线程安全，采用synchronized同步代码块对clean方法上锁
    synchronized void remove(String bearName) {
        while (total < 20) {
            try {
                //当罐子里蜂蜜的量小于20时，调用该方法的线程进入等待列表
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        total -= 20;
        this.notifyAll();
        System.out.println("\r\n" + "Y(●￣(ｴ)￣●)Y " + bearName + "把蜂蜜吃了。。。。" + "\r\n" + "剩余：" + total);
    }
}
