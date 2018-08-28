package lxpsee.lxpsee.javaday05.gof.singleinstance.lazy;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/15 16:07.
 * <p>
 * 普通的懒汉式,可能出现线程安全问题
 */
public class Singleton {

    private static Singleton singleton;

    private Singleton() {

    }

    public static Singleton getSingleton() {
        if (singleton == null) {
            singleton = new Singleton();
        }

        return singleton;
    }
}
