package lxpsee.top.wc.fourscala

import org.apache.spark.{SparkConf, SparkContext}

/**
  * The world always makes way for the dreamer
  * Created by 努力常态化 on 2018/10/30 15:29.
  */
object WCGroupByKey {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("WC1")
    conf.setMaster("local[2]")

    val sc = new SparkContext(conf)
    val rdd1 = sc.textFile("D:\\workDir\\otherFile\\scala\\stu.txt", 2)
    val rdd2 = rdd1.map(line => {
      val key = line.split(" ")(3)
      (key, line)
    })
    val rdd3 = rdd2.groupByKey()
    rdd3.collect().foreach(t => {
      val key = t._1
      println(key + " -----------------------")
      for (e <- t._2) {
        println(e)
      }
    })
  }

}
