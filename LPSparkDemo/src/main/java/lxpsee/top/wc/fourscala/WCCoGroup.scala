package lxpsee.top.wc.fourscala

import org.apache.spark.{SparkConf, SparkContext}

/**
  * The world always makes way for the dreamer
  * Created by 努力常态化 on 2018/10/30 16:27.
  */
object WCCoGroup {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("WC4")
    conf.setMaster("local[3]")
    val sc = new SparkContext(conf)

    val rdd1 = sc.textFile("D:\\workDir\\otherFile\\scala\\cogroup-1.txt", 3)
    val rdd2 = rdd1.map(line => {
      var arr = line.split(",")
      (arr(0).toInt, arr(1))
    })

    val rdd3 = sc.textFile("D:\\workDir\\otherFile\\scala\\cogroup-2.txt", 3)
    val rdd4 = rdd3.map(line => {
      val arr = line.split(" ")
      (arr(0).toInt, arr(1))
    })

    val rdd = rdd2.cogroup(rdd4).sortByKey()
    rdd.collect().foreach(t => {
      val key = t._1
      println(key + "-----------")
      for (e <- t._2._1) {
        print(e + " ")
      }
      println()
      for (e <- t._2._2) {
        print(e + " ")
      }
      println()
    })
  }

}
