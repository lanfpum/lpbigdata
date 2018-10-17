package lxpsee.top.storm.group.global;

import lxpsee.top.storm.util.Utils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/12 16:37.
 */
public class WCSplitBolt implements IRichBolt {
    private TopologyContext context;
    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        this.context = context;
    }

    @Override
    public void execute(Tuple tuple) {
        String line = tuple.getString(0);
        Utils.sendToLocalhost(this, line);
        String[] arr = line.split(" ");

        for (String s : arr) {
            collector.emit(new Values(s, 1));
        }
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word", "count"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
