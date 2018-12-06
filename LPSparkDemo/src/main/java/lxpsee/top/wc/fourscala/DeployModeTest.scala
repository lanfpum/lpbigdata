package lxpsee.top.wc.fourscala

import java.net.{InetAddress, Socket}

import org.apache.spark.{SparkConf, SparkContext}

/**
  * The world always makes way for the dreamer
  * Created by 努力常态化 on 2018/11/15 22:59.
  */
object DeployModeTest {

  def printInfo(str: String): Unit = {
    val ip = InetAddress.getLocalHost.getHostAddress
    val sock = new Socket("192.168.217.205", 8888)
    val out = sock.getOutputStream
    out.write((ip + ":" + str + "\r\n").getBytes())
    out.flush()
    sock.close()
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("DeployModeTest")
    conf.setMaster("spark://ip201:7077")
    conf.setMaster("yarn")
    val sc = new SparkContext(conf)
    printInfo("main() start .............")

    val rdd = sc.parallelize(1 to 10, 3)
    val rdd1 = rdd.map(e => {
      printInfo("map () " + e)
      e * 2
    })
    val rdd2 = rdd1.repartition(2)
    val rdd3 = rdd2.map(e => {
      printInfo("after repartition map()" + e)
      e
    })
    val res = rdd3.reduce((a, b) => {
      printInfo("reduce() " + a + " + " + b)
      a + b
    })

    printInfo("driver : " + res + "")
  }

}
