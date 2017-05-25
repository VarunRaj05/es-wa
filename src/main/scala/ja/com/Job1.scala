package ja.com
import java.util.regex.Matcher

import ja.com.Common.cdxItem
import ja.conf.JobSparkConf

import scala.collection.mutable.ListBuffer

// import ja.conf._
/**
  * Created by Ja on 21/05/2017.
  */

object Job1 {
  case class t(x : Long, y: String)
  def main(args: Array[String]): Unit = {

    val s = "com,google)/ 20090831083204 http://www.google.com/ text/html 302 MRIC3B6QBCMMI7MLO76A2P5ONACU4L7H - - 335 26267 EA-TNA-0709-biglotteryfund.org.uk-p-20090831083143-00000.arc.gz"
    val sp = s.split(" ")
    sp.foreach(println)
    val txtRDD = JobSparkConf.sc.textFile("C:\\Users\\Ja\\Google Drive\\srcfile\\Note\\srcfile\\EA-TNA-0709-biglotteryfund.org.uk-p-20090831083143-00000.cdx")
    val rddLines = txtRDD.mapPartitionsWithIndex { (idx, iter) => if (idx == 0) iter.drop(1) else iter }
    import JobSparkConf.sqlContext.implicits._
    val cdxItems = rddLines.map(x => cdxItem(x.split(" ")(0)
      ,x.split(" ")(1),x.split(" ")(2), x.split(" ")(3),
      x.split(" ")(4),x.split(" ")(5), x.split(" ")(6),
       x.split(" ")(7),x.split(" ")(8),x.split(" ")(9))
    ).toDF()
    // robots.txt, .js and, .json extensions, and any files which returned an HTTP status code of 400
    val filteredCDXitems = cdxItems
      .filter(!(cdxItems("a_origina_url").endsWith("robots.txt")
        || cdxItems("a_origina_url").endsWith(".js")
        || cdxItems("a_origina_url").endsWith(".json")
        ))

// s_response_code from 400  to 511
    import ja.com.Common._


    val filteredCDXitems1 = filteredCDXitems
      .filter(!(filteredCDXitems("s_response_code").between("400", "511")))
       .withColumn("New_URL", toGetURLnew(filteredCDXitems("a_origina_url")))

    // toGetURL(txtRDD).toString

    println(filteredCDXitems1)
    filteredCDXitems1.show(10000,truncate = false)
    filteredCDXitems1.registerTempTable("mainT")

    val inscopeDomains = JobSparkConf.sc.textFile("C:\\Users\\Ja\\Google Drive\\oldfiles\\Note\\srcfile\\SearchWhitelist\\dns_*")
    val domainsDf =   inscopeDomains.map( x => {
              if(x.indexOf("/") >= 0 )
                x.substring(0,x.indexOf("/")).replaceAll("\"", "")
              else
                x.replaceAll("\"", "")
    }).toDF("domain")


    println("count 1=" + domainsDf.count())
    domainsDf.registerTempTable("domainT")

    domainsDf.show(10)
    val joinedDF = JobSparkConf.sqlContext.sql("select mainT.* from mainT inner join domainT" +
      " ON mainT.New_URL LIKE concat('%',domainT.domain,'%') ")
    joinedDF.registerTempTable("joinedTable")
    joinedDF.show(100)



    val txtArcRDD = JobSparkConf.sc.textFile("C:\\Users\\Ja\\Google Drive\\srcfile\\Note\\srcfile\\EA-TNA-0709-biglotteryfund.org.uk-p-20090831083143-00000.arc")
    val indexedArcRDD = txtArcRDD.zipWithIndex
    import JobSparkConf.sqlContext.implicits._
    val mainDF = indexedArcRDD.toDF("line", "linenumber")
    mainDF.registerTempTable("tmp1")
    val query  = "Select tmp1.linenumber, tmp1.line from tmp1 " +
      " join (select linenumber from tmp1 where trim(line) = '') as t2 " +
      " on tmp1.linenumber = t2.linenumber + 1 where trim(tmp1.line) <> '' "
      //" left join (select distinct m_mime_type_of_original_document mime from joinedTable) as t3 "
      //" on tmp1.line like concat('%',t3.mime,'%') where trim(tmp1.line) <> '' "

    val dfMimes = joinedDF.select(joinedDF("m_mime_type_of_original_document")).distinct().toDF("mime")
  //  dfMimes.registerTempTable("mimestable")
    dfMimes.show(100)
    //dfMimes.count()   // TODO : 1
    //val broadcastVar = sc.broadcast(Array(1, 2, 3))
    var lstMimes = new ListBuffer[String]()
    val mlist1 = dfMimes.collect()
    println("fk")
    val mlist = mlist1.foreach(x => {
      println(x)
      lstMimes += x.getAs[String]("mime")
    })
    val broadcastVar = JobSparkConf.sc.broadcast(lstMimes.toList)

    val f1= JobSparkConf.sqlContext.sql(query).select("linenumber", "line").rdd

    println("fk2")
    import java.util.regex.Pattern
    val pattern = Pattern.compile("(\\d{14})")
    val f4 = f1.map(x => {
      val ln = x.getAs[Long]("linenumber")
      val line = x.getAs[String]("line")
      if(lstMimes.exists(line.contains)) {
        val matcher: Matcher = pattern.matcher(line)
        if(matcher.find)
          t(ln, line)
        else
          t(0, "")
      }
      else {
        t(0, "")
      }
    }).toDF()

    val datalines = f4.filter(!f4("x").equalTo(0) ).sort(f4("x"))
    datalines.show(1000, truncate = false)

    // http://www.biglotteryfund.org.uk/index/sifr-print.css 77.108.154.94 20090831083540 text/css 706

   /* val datalines = f1.collect().toMap() (x => {
      if(lstMimes.contains(x.getAs[String]("line")))
        Some(x)
      else
        None
    })*/


    /*
    f1.registerTempTable("firstJtoArc")
    println(f1.count())




    //val f3 = f1.join(dfMimes, f1("line").contains(dfMimes("m")), "inner").select(f1("line"), f1("linenumber"))
    f3.show(100)

    val query2  = "Select firstJtoArc.linenumber, firstJtoArc.line from firstJtoArc " +
      " join mimestable "
    " on firstJtoArc.line like concat('%',mimestable.m,'%')"

    val f2= JobSparkConf.sqlContext.sql(query2)
    println("**** f1")
    f1.show(30)
    println("**** f2")
    f2.show(30)*/

//http://www.google.com/ 209.85.227.99 20090831083204 text/html

    val lookupFile= JobSparkConf.sc.textFile("C:\\Users\\Ja\\Google Drive\\srcfile\\Note\\srcfile\\EA-TNA-0309-3719-0-20090518081242-00000.arc")

    val processedBits = lookupFile.map( line => {


    })
//       filteredCDXitems1.show(10, truncate = false)
    //
    //    val table1 = filteredCDXitems1.registerTempTable("table1")
    //
    //    val CDXSelect = JobSparkConf.sc
    //      .select(
    //      "massaged_url",
    //      "b_date",
    //      "a_origina_url",
    //      "m_mime_type_of_original_document",
    //      "s_response_code",
    //      "K_FBIS", "r_redirect",
    //      "code_abrevation",
    //      "V_compressed_arc_file_offset",
    //      "file_name" from "table1"
    //    ).toDF()

  //  println(table1)
//
//    import ja.com.Common.toGetURLnew
//    val filteredCDXitems2 = filteredCDXitems1
//      .withColumn("new_url",toGetURLnew(a_origina_url ))



  }
}
