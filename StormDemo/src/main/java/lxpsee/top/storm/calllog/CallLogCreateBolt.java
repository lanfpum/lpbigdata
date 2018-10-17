package lxpsee.top.storm.calllog;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * 创建CallLog日志的Bolt
 * <p>
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/11 17:34.
 */
public class CallLogCreateBolt implements IRichBolt {
    private OutputCollector outputCollector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.outputCollector = collector;
    }

    @Override
    public void execute(Tuple tuple) {
        // 处理通话记录，产生新的tuple并发送
        String from = tuple.getString(0);
        String to = tuple.getString(1);
        Integer duration = tuple.getInteger(2);

        outputCollector.emit(new Values(from + " --- " + to, duration));
    }

    @Override
    public void cleanup() {

    }

    /**
     * 设置输出字段的名称
     *
     * @param declarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("call", "duration"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
