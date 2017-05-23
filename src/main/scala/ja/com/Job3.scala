package ja.com

import ja.conf.JobSparkConf

object Job3 {
  def main(args: Array[String]): Unit = {

    val txtRDD = JobSparkConf.sc.textFile("C:\\Users\\Ja\\Google Drive\\srcfile\\Note\\srcfile\\EA-TNA-0709-biglotteryfund.org.uk-p-20090831083143-00000.arc")
    val tstamp = txtRDD.filter(line => line.contains("20090831083146"))
    val turl = tstamp.filter(line => line.contains("http://www.biglotteryfund.org.uk/robots.txt"))

    turl.take(1000).foreach(println)

  }
}
