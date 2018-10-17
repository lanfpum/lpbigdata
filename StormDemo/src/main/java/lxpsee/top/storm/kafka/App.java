package lxpsee.top.storm.kafka;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import java.util.UUID;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/17 15:49.
 */
public class App {
    public static void main(String[] args) {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        String zkConnString = "ip201:2181";
        BrokerHosts hosts = new ZkHosts(zkConnString);
        SpoutConfig spoutConfig = new SpoutConfig(hosts, "test2", "/test2", UUID.randomUUID().toString());
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

        topologyBuilder.setSpout("kafkaSpout", kafkaSpout).setNumTasks(2);
        topologyBuilder.setBolt("splitBolt", new WCSplitBolt(), 2).shuffleGrouping("kafkaSpout").setNumTasks(3);

        Config config = new Config();
        config.setNumWorkers(2);
        config.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("wc", config, topologyBuilder.createTopology());
    }
}
