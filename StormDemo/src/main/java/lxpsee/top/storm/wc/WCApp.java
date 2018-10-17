package lxpsee.top.storm.wc;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/12 16:50.
 */
public class WCApp {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("wcspout", new WCSpout(), 3).setNumTasks(3);
        builder.setBolt("splitBolt", new WCSplitBolt(), 4).shuffleGrouping("wcspout").setNumTasks(4);
        builder.setBolt("countBolt", new WCCountBolt(), 5).fieldsGrouping("splitBolt", new Fields("word")).setNumTasks(5);
//        builder.setSpout("wcspout", new WCSpout());
//        builder.setBolt("splitBolt", new SplitBolt()).shuffleGrouping("wcspout");
//        builder.setBolt("countBolt", new WCCountBolt()).fieldsGrouping("splitBolt", new Fields("word"));

        Config config = new Config();
        config.setNumWorkers(2);
        config.setDebug(true);

        /*LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("wc", config, builder.createTopology());
        Thread.sleep(10000);
        cluster.shutdown();*/
        StormSubmitter.submitTopology("wc", config, builder.createTopology());
    }
}
