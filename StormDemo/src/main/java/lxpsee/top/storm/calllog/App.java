package lxpsee.top.storm.calllog;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/12 08:03.
 */
public class App {
    public static void main(String[] args) throws Exception {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("callSpout", new CallLogSpout());
        topologyBuilder.setBolt("callCreateBolt", new CallLogCreateBolt()).shuffleGrouping("callSpout");
        topologyBuilder.setBolt("callCounterBolt", new CallLogCounterBolt()).fieldsGrouping("callCreateBolt", new Fields("call"));

        Config config = new Config();
        config.setDebug(true);

        /*LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("callStorm", config, topologyBuilder.createTopology());
        Thread.sleep(10000);
        cluster.shutdown();*/
        StormSubmitter.submitTopology("callTop", config, topologyBuilder.createTopology());
    }
}
