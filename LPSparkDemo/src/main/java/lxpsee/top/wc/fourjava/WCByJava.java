package lxpsee.top.wc.fourjava;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/10/29 08:31.
 */
public class WCByJava {
    public static void main(String[] args) {
//        SparkConf sparkConf = new SparkConf();
////        sparkConf.setAppName("javaWC");
////        sparkConf.setMaster("local");
//
//        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
//        JavaRDD<String> rdd1 = javaSparkContext.textFile(args[0]);
//
//        JavaRDD<String> rdd2 = rdd1.flatMap(new FlatMapFunction<String, String>() {
//            public Iterator<String> call(String s) throws Exception {
//                List<String> list = new ArrayList<String>();
//                String[] arr = s.split(" ");
//
//                for (String splitStr : arr) {
//                    list.add(splitStr);
//                }
//
//                return list.iterator();
//            }
//        });
//
//        JavaPairRDD<String, Integer> rdd3 = rdd2.mapToPair(new PairFunction<String, String, Integer>() {
//            public Tuple2<String, Integer> call(String s) throws Exception {
//                return new Tuple2<String, Integer>(s, 1);
//            }
//        });
//
//        JavaPairRDD<String, Integer> rdd4 = rdd3.reduceByKey(new Function2<Integer, Integer, Integer>() {
//            public Integer call(Integer v1, Integer v2) throws Exception {
//                return v1 + v2;
//            }
//        });
//
//        List<Tuple2<String, Integer>> collect = rdd4.collect();
//
//        for (Tuple2<String, Integer> tuple2 : collect) {
//            System.out.println(tuple2._1 + ":" + tuple2._2);
//        }
        getCPUCoreNO();

    }

    public static void getCPUCoreNO() {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

}
