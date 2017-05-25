package ja.temp

import ja.conf.JobSparkConf
object Job3Filter {

  def main(args: Array[String]): Unit = {

    val txtRDD = JobSparkConf.sc.textFile("C:\\Users\\Ja\\Google Drive\\srcfile\\Note\\srcfile\\EA-TNA-0709-biglotteryfund.org.uk-p-20090831083143-00000.arc")
    val indexedRDD = txtRDD.zipWithIndex
    import JobSparkConf.sqlContext.implicits._
    val mainDF = indexedRDD.toDF("line", "linenumber")

    mainDF.registerTempTable("tmp1")

    val query  = "Select t2.linenumber,tmp1.linenumber, tmp1.line from tmp1 " +
                     " join (select linenumber from tmp1 where trim(line) = '') as t2 " +
                      " on tmp1.linenumber = t2.linenumber + 1 where trim(tmp1.line) <> ''"

    val f1= JobSparkConf.sqlContext.sql(query)

    f1.show(1000, truncate = false )

    /*val tstamp = txtRDD.filter(line => line.contains("20090831083146"))
    val turl = tstamp.filter(line => line.contains("http://www.biglotteryfund.org.uk/robots.txt"))

    val matchingLineAndLineNumberTuples = txtRDD.zipWithIndex().filter({
      case (line, lineNumber) => line.contains("20090831083146")
    }).collect

    turl.take(1000).foreach(println)
    matchingLineAndLineNumberTuples.take(1000).foreach(println)
*/
    // *********************************
//    val rddLines = txtRDD.mapPartitionsWithIndex {
//      (idx, iter) => if (idx == 0) iter.drop(1) else iter }
//    val cdxItems = rddLines.map(x => cdxItem(x))
//    cdxItems.collect().foreach(println)
 // *********************************

/*    var rows = 0
    var loopvar = cdxItems
    var loopIt = loopvar.iterator
      rows = loopvar.length

    while (loopIt.hasNext) {
      val loopR = loopIt.next()
      val qLoopLabel = loopR._2
      val loopvarR = loopR

      println("loopvarR" )
      println("loopvarR =" + loopvarR)
    }*/

    // *********************************
  }
}
/*
    var v1 = ""

    var matchingLineAndLineNumberTuples12 =
      if (txtRDD != null &&
        txtRDD != "" &&
        txtRDD.toString.indexOf("20090831083146") >= 0 &&
        txtRDD.toString.indexOf("http://www.biglotteryfund.org.uk/robots.txt") >= 0
      )
        v1 = matchingLineAndLineNumberTuples12
        matchingLineAndLineNumberTuples12
  }

}

*/

/*
  val txtRDD = s
if (s != null && s != "" && s.toString.indexOf("/") >= 0) {

val V2 =

(
s.substring((s.lastIndexOf("/") - 2), (s.lastIndexOf("/") + 1)) +
s.substring((s.lastIndexOf("/") - 5), (s.lastIndexOf("/") - 3)) + "/" +
s.substring(s.lastIndexOf("/") + 1, 10)
)
retvalue = V2
}
retvalue
*/
