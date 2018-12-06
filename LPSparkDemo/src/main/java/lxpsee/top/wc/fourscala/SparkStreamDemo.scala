package lxpsee.top.wc.fourscala

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * The world always makes way for the dreamer
  * Created by 努力常态化 on 2018/11/5 15:37.
  */
object SparkStreamDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("SparkStreamDemo")
    val ssc = new StreamingContext(conf, Seconds(10))
    val line = ssc.socketTextStream("localhost", 9999)
    val words = line.flatMap(_.split(" "))
    val pairs = words.map((_, 1))
    val count = pairs.reduceByKey(_ + _)
    count.print()
    ssc.start()
    ssc.awaitTermination()
  }

}
