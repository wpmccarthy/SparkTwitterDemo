import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by Will on 02/09/2014.
 */

// Trait that sets up connection with cassandra when creating a SparkContext. It also adds the fat jar made with sbt assembly to the spark configuration.
// This works with a single cassandra node running on localhost

trait ConnectsToCassandra {

  def connectToCassandra(): SparkContext= {

    val conf = new SparkConf(true).set("spark.cassandra.connection.host", "localhost")
      .setJars(Array("target/scala-2.10/SparkTwitterDemo-assembly-1.0.jar"))
      .set("spark.cleaner.ttl", "3600")
      .setAppName("SparkTwitterApp")

    Logger.getRootLogger.setLevel(Level.WARN)

    new SparkContext("spark://127.0.0.1:7077", "Cassandra Connector Test", conf)

  }
}
