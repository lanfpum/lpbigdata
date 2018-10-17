package lxpsee.top.storm.calllog;

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
 * Created by 努力常态化 on 2018/10/11 17:17.
 * <p>
 * Spout类,负责产生数据流
 */
public class CallLogSpout implements IRichSpout {
    // spout输出收集器
    private SpoutOutputCollector collector;
    // 上下文
    private TopologyContext      context;
    // 是否完成
    private boolean              completed = false;

    // 随机发生器
    private Random  randomGenerator = new Random();
    private Integer idx             = 0;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        this.context = topologyContext;
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

    /**
     * 下一个元组
     */
    @Override
    public void nextTuple() {
        if (idx <= 1000) {
            List<String> mobileNumbers = new ArrayList<String>();
            mobileNumbers.add("18011112222");
            mobileNumbers.add("17033334444");
            mobileNumbers.add("19055556666");
            mobileNumbers.add("15077778888");
            idx++;
            Integer localIdx = 0;

            if (localIdx < 100 && idx < 1000) {
                String caller = mobileNumbers.get(randomGenerator.nextInt(4));
                String callee = mobileNumbers.get(randomGenerator.nextInt(4));

                while (caller.equals(callee)) {
                    callee = mobileNumbers.get(randomGenerator.nextInt(4));
                }

                int callTime = randomGenerator.nextInt(60);

                this.collector.emit(new Values(caller, callee, callTime));
            }
        }
    }

    @Override
    public void ack(Object o) {

    }

    @Override
    public void fail(Object o) {

    }

    /**
     * 定义输出的字段名称
     *
     * @param outputFieldsDeclarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("from", "to", "duration"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
