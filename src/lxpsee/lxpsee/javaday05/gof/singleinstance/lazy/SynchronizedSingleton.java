package lxpsee.lxpsee.javaday05.gof.singleinstance.lazy;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/15 16:15.
 * <p>
 * 线程安全的懒汉式：锁的范围太大
 * 他效率很低，因为99%情况下不需要同步
 */
public class SynchronizedSingleton {

    private static SynchronizedSingleton instance;

    private SynchronizedSingleton() {

    }

    public static synchronized SynchronizedSingleton getInstance() {
        if (instance == null) {
            instance = new SynchronizedSingleton();
        }

        return instance;
    }
}
