package lxpsee.top.wc.fourscala

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
  * The world always makes way for the dreamer
  * Created by 努力常态化 on 2018/11/1 17:41.
  */
object Cache {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("WC5")
    conf.setMaster("local[2,3]")
    val sc = new SparkContext(conf)

    val rdd = sc.parallelize(1 to 20)
    val rdd2 = rdd.map(e => {
      var str = java.net.InetAddress.getLocalHost.getHostAddress
      str = str + " : " + Thread.currentThread().getName + "\r\n"
      val soket = new java.net.Socket("ip201", 8888)
      val out = soket.getOutputStream()
      out.write(str.getBytes())
      out.flush()
      out.close()
      soket.close()
      e
    })

    //    rdd2.cache()

    rdd2.persist(StorageLevel.DISK_ONLY)
    println(rdd2.reduce(_ + _))
    rdd2.unpersist()
    println(rdd2.reduce(_ + _))
  }

}
