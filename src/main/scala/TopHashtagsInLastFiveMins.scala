/**
 * Created by Will on 05/09/2014.
 */

import com.datastax.spark.connector._
import com.github.nscala_time.time.Imports._
import org.apache.spark.SparkContext._

object TopHashtagsInLastFiveMins extends ConnectsToCassandra {
  def printTopHashtags(keyspace: String, table: String, time: DateTime){

    val sparkContext = connectToCassandra()

    //filtering with filter as cannot get where clause to work

    val hashtags = sparkContext.cassandraTable[(Int, String, DateTime)](keyspace, table)
                               .select("count", "hashtag", "minute_start")
                               .filter(rdd => rdd._3.isAfter(time))

    val popular = hashtags.map(rdd => (rdd._2, rdd._1))
                          .reduceByKey(_ + _)
                          .map({case (a: String, b: Int) => (b,a)})
                          .sortByKey(false)
                          .take(10)

    println(Console.CYAN + "Most popular hashtags in the last 5 minutes:" + Console.BLACK)

    for (tag <- popular){
      println(Console.CYAN + tag + Console.BLACK)
    }

  }

  def main (args: Array[String]) {
    printTopHashtags("twitter", "hashtags_by_minute", DateTime.now.second(0).millis(0).minusMinutes(5))
  }

}
