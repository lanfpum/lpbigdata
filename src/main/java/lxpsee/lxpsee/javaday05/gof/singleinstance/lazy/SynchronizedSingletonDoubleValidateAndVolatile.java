package lxpsee.lxpsee.javaday05.gof.singleinstance.lazy;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/15 16:24.
 * <p>
 * 完整的懒汉式
 */
public class SynchronizedSingletonDoubleValidateAndVolatile {
    private static volatile SynchronizedSingletonDoubleValidateAndVolatile instance;

    private SynchronizedSingletonDoubleValidateAndVolatile() {

    }

    public static SynchronizedSingletonDoubleValidateAndVolatile getInstance() {
        if (instance == null) {
            synchronized (SynchronizedSingletonDoubleValidateAndVolatile.class) {
                if (instance == null) {
                    instance = new SynchronizedSingletonDoubleValidateAndVolatile();
                }
            }
        }

        return instance;
    }
}
