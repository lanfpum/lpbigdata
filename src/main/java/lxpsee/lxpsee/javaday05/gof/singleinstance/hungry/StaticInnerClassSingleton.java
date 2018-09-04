package lxpsee.lxpsee.javaday05.gof.singleinstance.hungry;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/12 19:26.
 * <p>
 * 定义一个私有的静态内部类：在静态内部类中初始化实例对象
 * 私有的构造方法,对外提供获取实例的静态方法
 * <p>
 * 与懒汉式一样达到懒加载的目的
 */
public class StaticInnerClassSingleton {
    private StaticInnerClassSingleton() {

    }

    public StaticInnerClassSingleton getInstance() {
        return SingletonHolder.INSTANCEE;
    }

    private static class SingletonHolder {
        private static final StaticInnerClassSingleton INSTANCEE = new StaticInnerClassSingleton();
    }
}
