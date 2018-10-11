package lxpsee.top;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/10 10:33.
 */
public class ProducerTest {
    @Test
    public void testProducer() {
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "ip202:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("request.required.acks", "1");

        ProducerConfig producerConfig = new ProducerConfig(properties);
        Producer<String, String> producer = new Producer<String, String>(producerConfig);

        KeyedMessage<String, String> msg = new KeyedMessage<String, String>("test2", "199", "hello motosssss!");
        producer.send(msg);
        System.out.println("send over!");
    }

    @Test
    public void testConsumer() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "ip201:2181");
        props.put("group.id", "g2");
        props.put("zookeeper.session.timeout.ms", "500");
        props.put("zookeeper.sync.time.ms", "250");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");

        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("test2", new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> msgs = Consumer.createJavaConsumerConnector(consumerConfig).createMessageStreams(map);
        List<KafkaStream<byte[], byte[]>> msgList = msgs.get("test2");

        for (KafkaStream<byte[], byte[]> kafkaStream : msgList) {
            ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();

            while (iterator.hasNext()) {
                byte[] message = iterator.next().message();
                System.out.println(new String(message));
            }
        }


    }

}
