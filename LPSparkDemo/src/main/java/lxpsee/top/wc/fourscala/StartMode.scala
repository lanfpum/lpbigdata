package lxpsee.top.wc.fourscala

import org.apache.spark.{SparkConf, SparkContext}

/**
  * The world always makes way for the dreamer
  * Created by 努力常态化 on 2018/11/1 17:01.
  */
object StartMode {
  var count = 4

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("WC5")
    conf.setMaster("local[2,3]")
    val sc = new SparkContext(conf)

    val rdd = sc.parallelize(0 to 20)
    val rdd2 = rdd.map(t => {
      val tName = Thread.currentThread().getName
      println(tName + " :" + t)

      if (count != 0) {
        count -= 1
        throw new Exception("xxxx")
      } else {
        t
      }

    })

    println(rdd2.reduce(_ + _))
  }
}
