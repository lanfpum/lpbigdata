package lxpsee.top.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/9/10 09:04.
 */
public class ZKTest {

    /**
     * 读取当前目录下的子节点
     */
    @Test
    public void test() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("192.168.217.201:2181,192.168.217.202:2181,192.168.217.203:2181", 5000, null);
        List<String> list = zooKeeper.getChildren("/a", null);

        for (String s : list) {
            System.out.println(s);
        }
    }

    /**
     * 读取所有目录结构
     */
    @Test
    public void lsAll() throws Exception {
        lsPath("/");
    }

    public void lsPath(String path) throws Exception {
        System.out.println(path);
        ZooKeeper zooKeeper = new ZooKeeper("192.168.68.201:2181", 5000, null);
        List<String> list = zooKeeper.getChildren(path, null);

        if (list.isEmpty() || list == null) {
            return;
        }

        for (String s : list) {
            if (path.equals("/")) {
                lsPath(path + s);
            } else {
                lsPath(path + "/" + s);
            }
        }
    }

    /**
     * 读取当前节点的数据
     */
    @Test
    public void readDate() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("192.168.68.201:2181", 5000, null);
        byte[] data = zooKeeper.getData("/a/c", null, null);
        System.out.println(new String(data));
    }

    /**
     * 创建临时节点，调试使用才能看到效果
     * <p>
     * Node does not exist: /a/c1 报错
     */
    @Test
    public void createNOde() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("192.168.68.201:2181", 5000, null);
        String s = zooKeeper.create("/a/c1", "toomslee".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        System.out.println(s);
    }

    /**
     * 测试观察者模式
     */
    @Test
    public void createWatch() throws Exception {
        final ZooKeeper zooKeeper = new ZooKeeper("192.168.68.201:2181", 5000, null);
        Stat stat = new Stat();
        Watcher watcher = new Watcher() {
            public void process(WatchedEvent event) {
                try {
                    System.out.println("数据已经修改了！！！");
                    byte[] data = zooKeeper.getData("/a/d", this, null);
                    System.out.println(new String(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        byte[] data = zooKeeper.getData("/a/d", watcher, stat);
        System.out.println(new String(data));

        while (true) {
            Thread.sleep(1000);
        }

    }


}
