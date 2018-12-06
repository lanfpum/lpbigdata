package lxpsee.top.wc.fourjava;

import org.apache.spark.sql.ColumnName;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/4 16:43.
 */
public class JDBCJava {
    public static void main(String[] args) {
        SparkSession session = SparkSession.builder().appName("JDBCSql").config("spark.master", "local").getOrCreate();
        String url = "jdbc:mysql://192.168.31.96:3306/lanp";
        String table = "persons";

        Dataset<Row> dataset = session.read()
                .format("jdbc")
                .option("url", url)
                .option("dbtable", table)
                .option("user", "root")
                .option("password", "123")
                .option("driver", "com.mysql.jdbc.Driver")
                .load();
        dataset.show();
        System.out.println("-------------------------------");
        Dataset<Row> dataset1 = dataset.select(new ColumnName("name"), new ColumnName("phone"));
        dataset1 = dataset1.where("phone like '135%'");
        dataset1 = dataset1.distinct();
        dataset1.show();

        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "123");
        properties.put("driver", "com.mysql.jdbc.Driver");

        dataset1.write().jdbc(url, "subPersons", properties);

    }
}
