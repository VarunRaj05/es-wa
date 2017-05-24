package ja.temp

import ja.com.Common.cdxItem
import ja.conf.JobSparkConf
import org.elasticsearch.spark.rdd.EsSpark

object Job2SaveToES {
  def main(args: Array[String]): Unit = {

    val s = "com,google)/ 20090831083204 http://www.google.com/ text/html 302 MRIC3B6QBCMMI7MLO76A2P5ONACU4L7H - - 335 26267 EA-TNA-0709-biglotteryfund.org.uk-p-20090831083143-00000.arc.gz"
    val sp = s.split(" ")
    sp.foreach(println)

    val txtRDD = JobSparkConf.sc.textFile("C:\\Users\\Ja\\Google Drive\\srcfile\\Note\\srcfile\\EA-TNA-0709-biglotteryfund.org.uk-p-20090831083143-00000.cdx")
    val rddLines = txtRDD.mapPartitionsWithIndex { (idx, iter) => if (idx == 0) iter.drop(1) else iter }
    val cdxItems = rddLines.map(x => cdxItem(x.split(" ")(0)
     ,x.split(" ")(1)
      ,x.split(" ")(2), x.split(" ")(3),
      x.split(" ")(4),x.split(" ")(5), x.split(" ")(6),
       x.split(" ")(7),x.split(" ")(8),x.split(" ")(9)
       )
    )
    cdxItems.collect().foreach(println)

    import ja.conf.JobSparkConf._
    val rdd = sc.makeRDD(Seq(cdxItems))

    EsSpark.saveToEs(rdd, "spark/docs" , Map("es.nodes" -> "192.168.0.56"))
  }
}

/*
import org.elasticsearch.spark.rdd.EsSpark/*
EsSpark.saveToEs(rdd, "spark/docs", Map("e*/s.nodes" -> "10.0.5.151"))*/
