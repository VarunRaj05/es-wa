package ja.com
import ja.conf.JobSparkConf

object Whitelistfiles {
  def main(args: Array[String]): Unit = {

    import ja.com.Common.toGetURL
    val txtRDD = JobSparkConf.sc.textFile("C:\\Users\\Ja\\Google Drive\\srcfile\\Note\\srcfile\\Search Whitelist\\in-scope-microsites.txt").toString()
    val cdxItems = toGetURL(txtRDD).toString
    println(cdxItems)




  }
}
