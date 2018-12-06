package lxpsee.top.wc.fourscala

import org.apache.spark.{SparkConf, SparkContext}

/**
  * The world always makes way for the dreamer
  * Created by 努力常态化 on 2018/10/30 15:57.
  */
object WCJoin {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("WC2")
    conf.setMaster("local[2]")
    val sc = new SparkContext(conf)

    val rddName = sc.textFile("D:\\workDir\\otherFile\\scala\\name.txt", 2)
    val rddName2 = rddName.map(line => {
      var arr = line.split(" ")
      (arr(0).toInt, arr(1))
    })

    val rddSco = sc.textFile("D:\\workDir\\otherFile\\scala\\sco.txt", 2)
    val rddSco2 = rddSco.map(line => {
      var arr = line.split(",")
      (arr(0).toInt, arr(1))
    })

    val rdd = rddName2.join(rddSco2).sortByKey()
    rdd.collect().foreach(t => {
      println(t._1 + " : " + t._2)
    })
  }
}
