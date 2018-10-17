package lxpsee.top.storm.group.ensure;

import lxpsee.top.storm.util.Utils;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.*;

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

    private Map<Long, String>  messages    = new HashMap<Long, String>();
    private Map<Long, Integer> failMessage = new HashMap<Long, Integer>();

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

        if (index < 3) {
            String line = stringList.get(r.nextInt(5));
            long ts = System.currentTimeMillis();
            collector.emit(new Values(line), ts);
            messages.put(ts, line);
            System.out.println(Utils.getInfo(this, line + " nextTuple() :" + ts));
            index++;
        }

    }

    @Override
    public void ack(Object msgId) {
        long ts = (Long) msgId;
        messages.remove(ts);

        System.out.println(this + "---- ack() :" + msgId + "------");
    }

    @Override
    public void fail(Object msgId) {
        long ts = (Long) msgId;
        Integer retryCount = failMessage.get(ts);
        retryCount = retryCount == null ? 0 : retryCount;

        if (retryCount >= 3) {
            messages.remove(ts);
            failMessage.remove(ts);
            System.out.println("retrycount more then 3");
        } else {
            collector.emit(new Values(messages.get(ts)), ts);
            System.out.println(this + " retry emit " + retryCount + "  !!!! " + msgId);
            retryCount++;
            failMessage.put(ts, retryCount);
        }

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
