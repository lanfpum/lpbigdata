package lxpsee.top.storm.group.global;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/12 16:50.
 */
public class WCApp {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("wcspout", new WCSpout()).setNumTasks(2);
        builder.setBolt("splitBolt", new WCSplitBolt(), 2).globalGrouping("wcspout").setNumTasks(2);

        Config config = new Config();
        config.setNumWorkers(2);
        config.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("wc", config, builder.createTopology());
//        Thread.sleep(10000);
//        StormSubmitter.submitTopology("wc", config, builder.createTopology());
    }
}
