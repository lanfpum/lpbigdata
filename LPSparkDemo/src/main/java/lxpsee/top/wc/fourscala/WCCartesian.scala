package lxpsee.top.wc.fourscala

import org.apache.spark.{SparkConf, SparkContext}

/**
  * The world always makes way for the dreamer
  * Created by 努力常态化 on 2018/10/30 16:42.
  */
object WCCartesian {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("WC5")
    conf.setMaster("local")
    val sc = new SparkContext(conf)

    val rdd1 = sc.parallelize(Array("tom", "kobe", "kelay", "curry"))
    val rdd2 = sc.parallelize(Array("1234", "1111", "2222", "33333"))
    val rdd = rdd1.cartesian(rdd2).sortByKey()

    rdd.collect().foreach(t => {
      println(t)
    })
  }

}
