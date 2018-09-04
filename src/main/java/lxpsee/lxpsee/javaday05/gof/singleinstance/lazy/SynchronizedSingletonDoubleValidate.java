package lxpsee.lxpsee.javaday05.gof.singleinstance.lazy;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/7/15 16:19.
 * <p>
 * 双重校验锁，将锁变小:通过使用同步代码块的方式减小了锁的范围。这样可以大大提高效率
 */
public class SynchronizedSingletonDoubleValidate {
    private static SynchronizedSingletonDoubleValidate instance;

    private SynchronizedSingletonDoubleValidate() {

    }

    public static SynchronizedSingletonDoubleValidate getInstance() {
        if (instance == null) {
            synchronized (SynchronizedSingletonDoubleValidate.class) {
                if (instance == null) {
                    instance = new SynchronizedSingletonDoubleValidate();
                }
            }
        }

        return instance;
    }

}
