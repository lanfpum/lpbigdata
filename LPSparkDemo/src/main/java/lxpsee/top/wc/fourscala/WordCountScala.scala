package lxpsee.top.wc.fourscala

import org.apache.spark.{SparkConf, SparkContext}

/**
  * The world always makes way for the dreamer
  * Created by 努力常态化 on 2018/10/26 18:03.
  */
object WordCountScala {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("wcSpark")
    conf.setMaster("local")

    val sc = new SparkContext(conf)
    val rdd1 = sc.textFile("D:\\workDir\\otherFile\\scala\\test.txt", 2)
    val rdd2 = rdd1.flatMap(line => {
      println("flatMap :" + line)
      line.split(" ")
    })
    val rdd3 = rdd2.map(word => {
      println("map :" + word)
      (word, 1)
    })

    val rdd4 = rdd3.reduceByKey(_ + _)
    val a = rdd4.collect()

    a.foreach(println)
  }

}
