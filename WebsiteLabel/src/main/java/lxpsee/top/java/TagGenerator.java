package lxpsee.top.java;

import lxpsee.top.websitelabel.ReviewTags;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.*;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/12/6 15:39.
 * <p>
 * * 根据文件生成标签
 * * 1.先对文件整行按制表符切割
 * * 2.对切割的文件进行过滤
 * * 3.对过滤后的切割文件进行变换，如 77287793 -> 音响效果好，干净卫生，服务热情
 * * 4.过滤掉评论数为空的数据
 * * 5.变换为：77287793 -> [音响效果好，干净卫生，服务热情]
 * * 6.压扁数据 77287793->音响效果好， 77287793->干净卫生，77287793->服务热情
 * * 7.(77287793,音响效果好) -> 1， (77287793,干净卫生) -> 1，(77287793,服务热情) -> 1
 * * 8.聚合数据 (77287793,音响效果好) -> 340
 * * 9.变换数据，设置为list，list中只有一个元素，为了后面的操作。 77287793 -> list(音响效果好,340)
 * * 10.77287793 -> list((音响效果好,340),(干净卫生,280)）
 * * 11.将数据按降序排序成 77287793 -> 音响效果好:500，干净卫生:240，服务热情:200
 */
public class TagGenerator {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("javaTag");
        sparkConf.setMaster("local[4]");
//        sparkConf.setMaster("spark://ip201:7077");
        JavaSparkContext context = new JavaSparkContext(sparkConf);

        JavaRDD<String> rdd1 = context.textFile("file:///D:/workDir/otherFile/scala/temptegs/temptags.txt");

        JavaRDD<String[]> rdd2 = rdd1.map(new Function<String, String[]>() {
            public String[] call(String s) throws Exception {
                return s.split("\t");
            }
        });

        JavaRDD<String[]> rdd3 = rdd2.filter(new Function<String[], Boolean>() {
            public Boolean call(String[] v1) throws Exception {
                return v1.length == 2;
            }
        });

        JavaPairRDD<String, String> rdd4 = rdd3.mapToPair(new PairFunction<String[], String, String>() {
            public Tuple2<String, String> call(String[] strings) throws Exception {
                return new Tuple2<String, String>(strings[0], ReviewTags.extractTags(strings[1]));
            }
        });

        JavaPairRDD<String, String> rdd5 = rdd4.filter(new Function<Tuple2<String, String>, Boolean>() {
            public Boolean call(Tuple2<String, String> v1) throws Exception {
                return v1._2.length() > 0;
            }
        });

        JavaPairRDD<String, String[]> rdd6 = rdd5.mapToPair(new PairFunction<Tuple2<String, String>, String, String[]>() {
            public Tuple2<String, String[]> call(Tuple2<String, String> v) throws Exception {
                return new Tuple2<String, String[]>(v._1, v._2.split(","));
            }
        });

        JavaPairRDD<String, String> rdd7 = rdd6.flatMapValues(new Function<String[], Iterable<String>>() {
            public Iterable<String> call(String[] v1) throws Exception {
                List<String> list = new ArrayList<String>();

                for (String s : v1) {
                    list.add(s);
                }

                return list;
            }
        });

        JavaPairRDD<Tuple2<String, String>, Integer> rdd8 = rdd7.mapToPair(new PairFunction<Tuple2<String, String>, Tuple2<String, String>, Integer>() {
            public Tuple2<Tuple2<String, String>, Integer> call(Tuple2<String, String> stringStringTuple2) throws Exception {
                return new Tuple2<Tuple2<String, String>, Integer>(stringStringTuple2, 1);
            }
        });

        JavaPairRDD<Tuple2<String, String>, Integer> rdd9 = rdd8.reduceByKey(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        JavaPairRDD<String, Tuple2<String, Integer>> rdd10 = rdd9.mapToPair(new PairFunction<Tuple2<Tuple2<String, String>, Integer>, String, Tuple2<String, Integer>>() {
            public Tuple2<String, Tuple2<String, Integer>> call(Tuple2<Tuple2<String, String>, Integer> t) throws Exception {
                return new Tuple2<String, Tuple2<String, Integer>>(t._1._1, new Tuple2<String, Integer>(t._1._2, t._2));
            }
        });

        JavaPairRDD<String, List<Tuple2<String, Integer>>> rdd11 = rdd10.mapToPair(new PairFunction<Tuple2<String, Tuple2<String, Integer>>, String, List<Tuple2<String, Integer>>>() {
            public Tuple2<String, List<Tuple2<String, Integer>>> call(Tuple2<String, Tuple2<String, Integer>> t) throws Exception {
                List<Tuple2<String, Integer>> list = new ArrayList<Tuple2<String, Integer>>();
                list.add(t._2);
                return new Tuple2<String, List<Tuple2<String, Integer>>>(t._1, list);
            }
        });

        JavaPairRDD<String, List<Tuple2<String, Integer>>> rdd12 = rdd11.reduceByKey(new Function2<List<Tuple2<String, Integer>>, List<Tuple2<String, Integer>>, List<Tuple2<String, Integer>>>() {
            public List<Tuple2<String, Integer>> call(List<Tuple2<String, Integer>> v1, List<Tuple2<String, Integer>> v2) throws Exception {
                v1.addAll(v2);
                return v1;
            }
        });

        JavaPairRDD<String, String> rdd13 = rdd12.mapToPair(new PairFunction<Tuple2<String, List<Tuple2<String, Integer>>>, String, String>() {
            public Tuple2<String, String> call(Tuple2<String, List<Tuple2<String, Integer>>> t) throws Exception {
                Set<Tuple2<String, Integer>> set = new TreeSet<Tuple2<String, Integer>>(new Tuple2Comparator());
                set.addAll(t._2);
                Iterator<Tuple2<String, Integer>> iterator = set.iterator();
                int index = 0;
                StringBuilder sb = new StringBuilder();

                while (iterator.hasNext()) {
                    if (index > 9) {
                        break;
                    }

                    Tuple2<String, Integer> next = iterator.next();
                    sb.append(next._1 + ":" + next._2 + ",");
                    index++;
                }

                String res = sb.substring(0, sb.length() - 1);
                return new Tuple2<String, String>(t._1, res);
            }
        });

        List<Tuple2<String, String>> list = rdd13.collect();

        for (Tuple2<String, String> t : list) {
            System.out.println(t._1 + " ------------> " + t._2);
        }

    }
}
