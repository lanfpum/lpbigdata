package lxpsee.lxpsee.javaday05.gof.singleinstance.hungry;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/12 17:26.
 * <p>
 * 饿汉模式的变种
 * 饿汉式单例，在类被加载的时候对象就会实例化。
 * 这也许会造成不必要的消耗，因为有可能这个实例根本就不会被用到。
 * 而且，如果这个类被多次加载的话也会造成多次实例化。
 */
public class StaticHungryManSingleton {
    private static StaticHungryManSingleton instance;

    static {
        instance = new StaticHungryManSingleton();
    }

    private StaticHungryManSingleton() {

    }

    public StaticHungryManSingleton getInstance() {
        return instance;
    }
}
