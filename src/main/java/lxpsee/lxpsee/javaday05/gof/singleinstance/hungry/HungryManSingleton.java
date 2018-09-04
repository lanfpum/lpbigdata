package lxpsee.lxpsee.javaday05.gof.singleinstance.hungry;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/12 17:18.
 * <p>
 * 饿汉式 单例模式
 * 私有的构造函数,在类内部实例化一个实例(在属性上实例化),对外提供获取实例的静态方法
 * <p>
 * 对于一个饿汉来说，他希望他想要用到这个实例的时候就能够立即拿到，而不需要任何等待时间,避免了线程安全问题
 */
public class HungryManSingleton {
    private static HungryManSingleton instance = new HungryManSingleton();

    private HungryManSingleton() {

    }

    public HungryManSingleton getInstance() {
        return instance;
    }
}
