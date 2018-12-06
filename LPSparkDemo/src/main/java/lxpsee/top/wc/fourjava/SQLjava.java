package lxpsee.top.wc.fourjava;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/4 15:14.
 */
public class SQLjava {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf();
        conf.setMaster("local");
        conf.setAppName("SQLJava");

        SparkSession session = SparkSession.builder().appName("SQLjava").config("spark.master", "local").getOrCreate();
        Dataset<Row> dataset = session.read().json("file:///D:\\workDir\\otherFile\\scala\\json.dat");

        dataset.createTempView("customers");
        dataset.show();

        Dataset<Row> dataset1 = session.sql("select * from customers where age > 15");
//        dataset1.show();
        dataset1.write().json("file:///D:\\workDir\\otherFile\\scala\\lp.dat");


      /*  Dataset<Row> dataset1 = session.sql("select * from customers where age > 15");
        dataset1.show();

        Dataset<Row> dataset2 = dataset.where("age > 15");
        dataset2.show();

        Dataset<Row> dataset3 = session.sql("select count(1) from customers");
        dataset3.show();

        System.out.println(dataset.count());*/

       /* JavaRDD<Row> rowJavaRDD = dataset.toJavaRDD();
        rowJavaRDD.collect().forEach(new Consumer<Row>() {
            @Override
            public void accept(Row row) {
                long age = row.getLong(0);
                long id = row.getLong(1);
                String name = row.getString(2);
                System.out.println(age + " : " + id + " : " + name);
            }
        });*/

    }
}
