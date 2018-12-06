package lxpsee.top.wc.fourjava;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function0;
import org.apache.spark.streaming.Minutes;
import org.apache.spark.streaming.Seconds;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/6 17:03.
 */
public class JavaSparkStreamingWordCountWindowsApp {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("hadoop.home.dir", "G:\\Java\\LANPENG\\HadoopInstalled\\hadoop-2.7.3");

        Function0 function0 = new Function0() {
            @Override
            public Object call() throws Exception {
                SparkConf sparkConf = new SparkConf();
                sparkConf.setMaster("local[4]");
                sparkConf.setAppName("WindowJava");
                JavaStreamingContext jsc = new JavaStreamingContext(sparkConf, Seconds.apply(5));
                JavaReceiverInputDStream<String> javaReceiverInputDStream = jsc.socketTextStream("localhost", 9999);

                JavaDStream<Long> javaDStream = javaReceiverInputDStream.countByWindow(Minutes.apply(24 * 60), Seconds.apply(10));
                javaDStream.print();

                jsc.checkpoint("file:///D:/workDir/otherFile/scala/check");
                return jsc;
            }
        };

        JavaStreamingContext javaStreamingContext = JavaStreamingContext.getOrCreate("file:///D:/workDir/otherFile/scala/check", function0);
        javaStreamingContext.start();
        javaStreamingContext.awaitTermination();
    }
}
