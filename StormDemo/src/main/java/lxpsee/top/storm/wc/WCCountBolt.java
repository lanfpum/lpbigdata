package lxpsee.top.storm.wc;

import lxpsee.top.storm.util.Utils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/12 16:44.
 */
public class WCCountBolt implements IRichBolt {
    private TopologyContext      context;
    private OutputCollector      collector;
    private Map<String, Integer> map;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        Utils.sendToClient(this, "WCCountBolt 的 prepare()", 9999);
        this.collector = collector;
        this.context = context;
        map = new HashMap<String, Integer>();
    }

    @Override
    public void execute(Tuple tuple) {
        Utils.sendToClient(this, "WCCountBolt 的 execute()", 9999);
        String word = tuple.getString(0);
        Integer count = tuple.getInteger(1);

        if (map.containsKey(word)) {
            map.put(word, map.get(word) + count);
        } else {
            map.put(word, 1);
        }

        collector.ack(tuple);
    }

    @Override
    public void cleanup() {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " ----------------  " + entry.getValue());
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
