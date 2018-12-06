package lxpsee.top.wc.fourscala

import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

/**
  * The world always makes way for the dreamer
  * Created by 努力常态化 on 2018/10/31 15:05.
  */
object WCDataLean {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("WC48")
    conf.setMaster("local[2]")
    val sc = new SparkContext(conf)

    val rdd1 = sc.textFile("D:\\workDir\\otherFile\\scala\\test.txt", 2)
    val rdd = rdd1.flatMap(_.split(" ")).map((_, 1)).map(t => {
      val word = t._1
      val r = Random.nextInt(100)
      (word + "_" + r, 1)
    }).reduceByKey(_ + _, 2).map(t => {
      val word = t._1
      val count = t._2
      val w = word.split("_")(0)
      (w, count)
    }).reduceByKey(_ + _, 2)

    rdd.collect().foreach(println)

    rdd.saveAsTextFile("D:\\workDir\\otherFile\\scala\\out")
  }

}
