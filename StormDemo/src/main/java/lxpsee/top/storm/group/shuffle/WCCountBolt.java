package lxpsee.top.storm.group.shuffle;

import lxpsee.top.storm.util.Utils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Collections;
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
        this.collector = collector;
        this.context = context;
        map = new HashMap<String, Integer>();
        map = Collections.synchronizedMap(this.map);

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    emitData();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }

    private void emitData() {
        synchronized (map) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                collector.emit(new Values(entry.getKey(), entry.getValue()));
            }

            map.clear();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(Tuple tuple) {
        String word = tuple.getString(0);
        Utils.sendToLocalhost(this, word);
        Integer count = tuple.getInteger(1);

        if (map.containsKey(word)) {
            map.put(word, map.get(word) + count);
        } else {
            map.put(word, count);
        }
    }

    @Override
    public void cleanup() {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " ----------------  " + entry.getValue());
        }
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
