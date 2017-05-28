package ja.temp

import ja.conf.JobSparkConf

object Job2SaveToES {
  def main(args: Array[String]): Unit = {
//    val rdd = JobSparkConf.sc.parallelize(List(1,2,3))
//    EsSpark.saveToEs(rdd, "spark/docs" , Map("es.nodes" -> "192.168.0.56"))

    import org.elasticsearch.spark.rdd.EsSpark

    // define a case class
    case class Trip(departure: String, arrival: String)

    val upcomingTrip = Trip("OTP", "SFO")
    val lastWeekTrip = Trip("MUC", "OTP")

    val rdd = JobSparkConf.sc.makeRDD(Seq(upcomingTrip, lastWeekTrip))
   // EsSpark.saveToEs(rdd, "spark/docs")

    EsSpark.saveToEs(rdd, "spark/docs", Map("es.mapping.id" -> "id"))
  }
}

/*
import org.elasticsearch.spark.rdd.EsSpark/*
EsSpark.saveToEs(rdd, "spark/docs", Map("e*/s.nodes" -> "10.0.5.151"))*/
