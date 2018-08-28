package lxpsee.lxpsee.javaday01.threadtest;

/**
 * 努力常态化  2018/6/26 15:09 The world always makes way for the dreamer
 * 证明多线程是串行运行的
 */
public class ThreadTest {

    /* @Test
     public void test() {
         new MyThread("tom").start();
         new MyThread("kobe").start();
     }
 */
    public static void main(String[] args) {
        new MyThread("T").start();
        new MyThread("C").start();
    }
}

class MyThread extends Thread {
    private String name;

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(name + " : " + i);
//                yield();
        }
    }
}