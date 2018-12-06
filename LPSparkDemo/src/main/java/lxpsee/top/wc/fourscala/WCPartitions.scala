package lxpsee.top.wc.fourscala

import org.apache.spark.{SparkConf, SparkContext}

/**
  * The world always makes way for the dreamer
  * Created by 努力常态化 on 2018/10/30 16:59.
  */
object WCPartitions {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("WC6")
    conf.setMaster("local[4]")
    val sc = new SparkContext(conf)

    val rdd = sc.textFile("D:\\workDir\\otherFile\\scala\\test.txt", 4)
    println("rdd part is :" + rdd.partitions.length)

    //    val rdd1 = rdd.coalesce(3)
    val rdd1 = rdd.repartition(8)
    val rdd2 = rdd1.flatMap(_.split(" "))
    println("rdd part is :" + rdd2.partitions.length)

    val rdd3 = rdd2.map((_, 1))
    println("rdd part is :" + rdd3.partitions.length)
  }

}
