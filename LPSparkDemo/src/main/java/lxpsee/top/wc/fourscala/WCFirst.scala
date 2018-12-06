package lxpsee.top.wc.fourscala

import org.apache.spark.{SparkConf, SparkContext}

/**
  * The world always makes way for the dreamer
  * Created by 努力常态化 on 2018/10/30 17:12.
  */
object WCFirst {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("WC6")
    conf.setMaster("local[4]")
    val sc = new SparkContext(conf)

    val rdd = sc.textFile("D:\\workDir\\otherFile\\scala\\test.txt", 4)
    println(rdd.first())
    rdd.take(3).foreach(println)
  }

}
