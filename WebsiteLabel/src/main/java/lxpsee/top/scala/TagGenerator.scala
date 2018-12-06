package lxpsee.top.scala

import lxpsee.top.websitelabel.ReviewTags
import org.apache.spark.{SparkConf, SparkContext}

/**
  * The world always makes way for the dreamer
  * Created by 努力常态化 on 2018/12/5 16:37.
  *
  * 根据文件生成标签
  * 1.先对文件整行按制表符切割
  * 2.对切割的文件进行过滤
  * 3.对过滤后的切割文件进行变换，如 77287793 -> 音响效果好，干净卫生，服务热情
  * 4.过滤掉评论数为空的数据
  * 5.变换为：77287793 -> [音响效果好，干净卫生，服务热情]
  * 6.压扁数据 77287793->音响效果好， 77287793->干净卫生，77287793->服务热情
  * 7.(77287793,音响效果好) -> 1， (77287793,干净卫生) -> 1，(77287793,服务热情) -> 1
  * 8.聚合数据 (77287793,音响效果好) -> 340
  * 9.变换数据，设置为list，list中只有一个元素，为了后面的操作。 77287793 -> list(音响效果好,340)
  * 10.77287793 -> list((音响效果好,340),(干净卫生,280)）
  * 11.将数据按降序排序成 77287793 -> 音响效果好:500，干净卫生:240，服务热情:200
  *
  */
object TagGenerator {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local[4]").setAppName("TagGenerator"))
    val poi_targs = sc.textFile("file:///D:/workDir/otherFile/scala/temptegs/temptags.txt")
    val poi_targList = poi_targs.map(e => e.split("\t"))
      .filter(e => e.length == 2)
      .map(e => e(0) -> ReviewTags.extractTags(e(1)))
      .filter(e => e._2.length > 0)
      .map(e => e._1 -> e._2.split(","))
      .flatMapValues(e => e)
      .map(e => (e._1, e._2) -> 1)
      .reduceByKey(_ + _)
      .map(e => e._1._1 -> List((e._1._2, e._2)))
      .reduceByKey(_ ::: _)
      .map(e => e._1 -> e._2.sortBy(_._2).reverse.take(10).map(a => a._1 + ":" + a._2.toString).mkString(","))
    poi_targList.map(e => e._1 + "\t" + e._2).saveAsTextFile("file:///D:/workDir/otherFile/scala/temptegs/out/res.txt")
  }

}
