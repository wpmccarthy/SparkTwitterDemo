
/**
 * Created by Will on 10/09/2014.
 */

import com.datastax.spark.connector._
import com.github.nscala_time.time.Imports._
import org.apache.spark.SparkContext._

//use run-main GetTagStats args
//This does not seem to work. Ideally I would use a where clause here but they are not completely supported right now

object GetTagStats extends ConnectsToCassandra{
  def main (args: Array[String]) {
    val sparkContext = connectToCassandra();

    val hashtags = sparkContext.cassandraTable[(String, DateTime, Int)]("twitter", "hashtags_by_name")
      .select("hashtag", "minute_start", "count")
      .filter(rdd => rdd._1==("#" + args(0)))
      .foreach{
        case (tag, time, count) => println("%s %s (%s tweets)".format(time, tag, count))
      }
  }
}
