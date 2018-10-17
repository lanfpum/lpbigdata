package lxpsee.top.storm.group.all;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/12 16:25.
 */
public class WCSpout implements IRichSpout {
    private TopologyContext      context;
    private SpoutOutputCollector collector;
    private List<String>         stringList;

    private Random r     = new Random();
    private int    index = 0;

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        this.context = context;
        stringList = new ArrayList<String>(5);
        stringList.add("hello tom and jim");
        stringList.add("hello jeck and green");
        stringList.add("find thank you too");
        stringList.add("nice to meet you me too you too");
        stringList.add("we all hate the world you and me tom and jim");
    }

    @Override
    public void close() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void nextTuple() {
        int taskId = 0;

        Map<Integer, String> map = context.getTaskToComponent();

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals("splitBolt")) {
                taskId = entry.getKey();
                break;
            }
        }

        if (index < 3) {
            String line = stringList.get(r.nextInt(5));
            collector.emitDirect(taskId, new Values(line));
//            Utils.sendToLocalhost(this, line);
            index++;
        }

    }

    @Override
    public void ack(Object msgId) {

    }

    @Override
    public void fail(Object msgId) {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("line"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
