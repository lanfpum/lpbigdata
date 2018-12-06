package lxpsee.top.wc.fourjava;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Seconds;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/5 15:47.
 */
public class SparkStreamJava {
    public static void main(String[] args) throws Exception {
        SparkConf sparkConf = new SparkConf();
        sparkConf.setMaster("local[2]");
        sparkConf.setAppName("SparkStreamJava");

        JavaStreamingContext jsc = new JavaStreamingContext(sparkConf, Seconds.apply(10));
//        jsc.checkpoint("D:\\workDir\\otherFile\\scala");
        JavaReceiverInputDStream<String> javaReceiverInputDStream = jsc.socketTextStream("localhost", 9999);
        JavaDStream<String> javaDStream = javaReceiverInputDStream.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                List<String> list = new ArrayList<>();
                String[] arr = s.split(" ");

                for (String a : arr) {
                    list.add(a);
                }

                return list.iterator();
            }
        });
        JavaPairDStream<String, Integer> pairDStream = javaDStream.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        });

       /* JavaPairDStream<String, Integer> pairDStream1 = pairDStream.updateStateByKey(new Function2<List<Integer>, Optional<Integer>, Optional<Integer>>() {
            @Override
            public Optional<Integer> call(List<Integer> v1, Optional<Integer> v2) throws Exception {
                Integer newCount = v2.isPresent() ? v2.get() : 0;
                System.out.println("old value :" + newCount);

                for (Integer integer : v1) {
                    System.out.println("new Value ； " + integer);
                    newCount += integer;
                }

                return Optional.of(newCount);
            }
        });*/

        JavaPairDStream<String, Integer> stream = pairDStream.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });
        stream.print();
        jsc.start();
        jsc.awaitTermination();
    }
}
