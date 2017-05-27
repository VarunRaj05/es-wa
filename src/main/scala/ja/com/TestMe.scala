package ja.com

object TestMe {
  def main(args: Array[String]): Unit = {

    val xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><swfData width=\"330\" height=\"300\" minflashversion=\"7\" src=\"video-player-2008.swf\"><photoUrl>open_meeting_sirclive.jpg</photoUrl><photoTitle><![CDATA[Sir Clive Booth and Diana Leat]]></photoTitle><recipient></recipient><project></project><copyright></copyright><date></date><programmes><![CDATA[]]></programmes><events>Open Meeting 2007</events><location></location><caption><![CDATA[Sir Clive Booth and Diana Leat]]></caption></swfData>"

    val raw = xml.XML.loadString(xmlString)
    raw.child.foreach(x => {
      if(!x.text.isEmpty)  print(x.text + ";")
    })
   /* val doc = Jsoup.parse(html)
    println(doc)

        import org.jsoup.Jsoup
        val html1 = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>"
        val doc1 = Jsoup.parse(html1)
        val text = doc1.body.text
    println(text)*/

    /*val dataLinePattern = Pattern.compile("(\\d{14})")

    val line = "12345678901234 12345678"
    val matcher: Matcher = dataLinePattern.matcher(line)
    println(matcher.toMatchResult)
*/

  }
}


// /*
///*
//    import org.jsoup.Jsoup
//    val html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>"
//    val doc = Jsoup.parse(html)
//    val text = doc.body.text
//
//
//    println("*****wikipedia")
//    val doc2 = Jsoup.connect("http://en.wikipedia.org/").get
//    //val newsHeadlines = doc.select("#mp-itn b a")
//    val newsHeadlines = doc2.select("#mp-itn b a")
//    println(newsHeadlines)
//    println("*****wikipedia text")
//    val html1 = doc2
//    val doc1 = doc
//    val text1 = doc1.body.text
//
//    println(text)
//    println(text1)
//
//    /*val prefix = "/home/tmp/date="
//    val dates =  Array("20140901", "20140902", "20140903", "20140904")
//    val datesRDD = JobSparkConf.sc.parallelize(dates, 2)
//
//println("datesRDD")
//    datesRDD.foreach(println)
//    println("datesWithPrefixRDD")
//
//    val datesWithPrefixRDD = datesRDD.map(s => prefix + s)
//    datesWithPrefixRDD.foreach(println)
//
///*
//    +----------+-------------+--------+---------------------------------------------------------------------------------------------------+--------------+---------+------------------------------------------------------------------------------------------------------------------------------------------------+-------------+--------------+---------+---------------------------------------------------------------------------------------------------+
//    |linenumber|isvalidheader|isheader|url                                                                                                |urltime       |mime     |line                                                                                                                                            |p_linenumberA|p_urltime     |p_mime   |p_url                                                                                              |
//    +----------+-------------+--------+---------------------------------------------------------------------------------------------------+--------------+---------+------------------------------------------------------------------------------------------------------------------------------------------------+-------------+--------------+---------+---------------------------------------------------------------------------------------------------+
//    |19        |true         |true    |dns:www.biglotteryfund.org.uk                                                                      |20090831083143|text/dns |dns:www.biglotteryfund.org.uk 213.251.150.194 20090831083143 text/dns 67                                                                        |19           |20090831083143|text/dns |dns:www.biglotteryfund.org.uk                                                                      |
//      |20        |false        |false   |                                                                                                   |              |         |20090831083143                                                                                                                                  |19           |20090831083143|text/dns |dns:www.biglotteryfund.org.uk                                                                      |
//*/
//
//        val p_linenumberA = 19
//        val p_urltime =    "20090831083143"
//        val p_mime =    "text/dns"
//        val p_url = "dns:www.biglotteryfund.org.uk"
//
//    // import org.apache.spark.sql.functions.{array,explode}
//
//    val prefix1 = "Original column + Derived fields, "
//    val dates1 =  Array(p_linenumberA, p_urltime, p_mime, p_url) // invoke
//    // val expanded = prefix1.withColumn("DerivedCol",explode(array(toExpand.map(col): _*)))
//    val datesRDD1 = JobSparkConf.sc.parallelize(dates1, 2)
//    println("datesRDD1" + datesRDD1)
//
//    val A = datesRDD1.map(s => prefix1 + s)
//    val B =  p_linenumberA +" " + p_urltime + " "+p_mime + " "+ p_url
//    A.foreach(println)
//    println("Original rows + Derived concatenated rows = " + B)
//*/
//
//  }
//}
//*/