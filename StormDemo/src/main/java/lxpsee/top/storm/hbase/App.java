package lxpsee.top.storm.hbase;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import java.util.UUID;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/17 16:46.
 */
public class App {
    public static void main(String[] args) {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        String zkConnString = "ip201:2181";
        BrokerHosts hosts = new ZkHosts(zkConnString);
        SpoutConfig spoutConfig = new SpoutConfig(hosts, "test4hbase", "/test4hbase", UUID.randomUUID().toString());
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

        topologyBuilder.setSpout("kafkaSpout", kafkaSpout).setNumTasks(1);
        topologyBuilder.setBolt("splitBolt", new SplitBolt()).shuffleGrouping("kafkaSpout").setNumTasks(1);
        topologyBuilder.setBolt("countBolt", new HBaseBolt()).fieldsGrouping("splitBolt", new Fields("word")).setNumTasks(2);

        Config config = new Config();
        config.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("wc", config, topologyBuilder.createTopology());
    }
}
