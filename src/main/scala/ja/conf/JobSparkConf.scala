package ja.conf

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.{SQLContext}
import org.slf4j.LoggerFactory


/**
  * To configure the Spark job
  *
  */

trait Conf {
  val conf: SparkConf
  val sqlContext: SQLContext

}
object JobSparkConf extends Conf{
  val conf = new SparkConf()
    .setAppName("Spark ETL Job").setMaster("local[1]")
  conf.set("es.index.auto.create", "false")
     .set("es.cluster", "pip6fRVRQkGRE4PQ9yqXKA")
    .set("es.node", "192.168.0.56")
    .set("es.port", "9200")
   // .set("es.nodes.discovery", "false")

  /*.set("spark.sql.sources.default", "json") Agwal997Rhaw8Z7r6RluMA
  .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
  .set("spark.sql.tungsten.enabled", "true")
  .set("spark.driver.allowMultipleContexts", "true")*/

  val sc = new SparkContext(conf)

  // Logging
  val sqlContext = new SQLContext(sc)

  System.setProperty("spark.ui.showConsoleProgress", "false")
  Logger.getLogger("org").setLevel(Level.WARN)
  Logger.getLogger("akka").setLevel(Level.WARN)

  // Enable snappy compression for Avro
  //   hiveContext.setConf("spark.sql.avro.compression.codec", "snappy")

  val log = LoggerFactory.getLogger(this.getClass.getName)
}

