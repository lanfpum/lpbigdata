package lxpsee.top.wc.fourjava;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/4 23:32.
 */
public class HiveJava {
    public static void main(String[] args) {
        SparkSession session = SparkSession.builder().appName("HiveJava").config("spark.master", "local").getOrCreate();
//        session.sql("use lpdb4hive");
        Dataset<Row> dataset = session.sql("select * from lpdb4hive.customers");
        dataset.show();
    }
}
