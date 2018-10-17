package lxpsee.top.storm.wc;

import lxpsee.top.storm.util.Utils;
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

    private Random r = new Random();

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        Utils.sendToClient(this, "Soput的open()", 7777);
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
        Utils.sendToClient(this, "Soput的nextTuple()", 7777);
        String line = stringList.get(r.nextInt(5));
        collector.emit(new Values(line));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
