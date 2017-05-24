package ja.com

import ja.conf.JobSparkConf

object Job3 {
  def main(args: Array[String]): Unit = {

    val txtRDD = JobSparkConf.sc.textFile("C:\\Users\\Ja\\Google Drive\\srcfile\\Note\\srcfile\\EA-TNA-0709-biglotteryfund.org.uk-p-20090831083143-00000.arc")
    val tstamp = txtRDD.filter(line => line.contains("20090831083146"))
    val turl = tstamp.filter(line => line.contains("http://www.biglotteryfund.org.uk/robots.txt"))

    val matchingLineAndLineNumberTuples = txtRDD.zipWithIndex().filter({
      case (line, lineNumber) => line.contains("20090831083146")
    }).collect

    turl.take(1000).foreach(println)
    matchingLineAndLineNumberTuples.take(1000).foreach(println)

//    var v1 = ""
//
//    var matchingLineAndLineNumberTuples12 =
//      if (txtRDD != null &&
//        txtRDD != "" &&
//        txtRDD.toString.indexOf("20090831083146") >= 0 &&
//        txtRDD.toString.indexOf("http://www.biglotteryfund.org.uk/robots.txt") >= 0
//      )
     //   v1 = matchingLineAndLineNumberTuples12
        //matchingLineAndLineNumberTuples12
  }


}


/*
if (s != null && s != "" && s.toString.indexOf("/") >= 0) {

val V2 =

(
s.substring((s.lastIndexOf("/") - 2), (s.lastIndexOf("/") + 1)) +
s.substring((s.lastIndexOf("/") - 5), (s.lastIndexOf("/") - 3)) + "/" +
s.substring(s.lastIndexOf("/") + 1, 10)
)
retvalue = V2
}

retvalue*/
