
/**
 * Created by Will on 02/09/2014.
 */

import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.twitter._
import com.datastax.spark.connector.streaming._
import com.github.nscala_time.time.Imports._

//This file has been used as a test for spark streaming with Cassandra. It requires cassandra tables to be set up before running.

object HashtagStreamToCassandra extends StreamsWithCassandra{

  def main(args: Array[String]): Unit ={

    TwitterCredentials.setCredentials()

    val ssc = createStreamingContext(10)

    println("Getting tweets...")

    val stream = TwitterUtils.createStream(ssc, None)

    val hashtags = stream.flatMap(status => status.getText.split(" ").filter(_.startsWith("#"))).map((_, 1))

    //10 second window for accumulating hashtags with count and saving to Cassandra.

    val hashtagsWithCount = hashtags.reduceByKeyAndWindow(_ + _, Seconds(10))
      .map{case (topic, count) => (count, topic)}
      .transform(_.sortByKey(false))
      .map{case (count, topic) => (DateTime.now.second(0).millis(0), topic, count)}

    hashtagsWithCount.saveToCassandra("twitter", "hashtags_by_minute", Seq("minute_start", "hashtag", "count"))

    //This second column family has hashtag as it's partition key

    hashtagsWithCount.saveToCassandra("twitter", "hashtags_by_name", Seq("minute_start", "hashtag",  "count"))

    //60 second window with 10 second sliding, printing the top 10 hashtags in the last 60 seconds, every 10 seconds.

    val topTweetsThisMinute = hashtags.reduceByKeyAndWindow(_ + _, Seconds(60))
      .map{case (topic, count) => (count, topic)}
      .transform(_.sortByKey(false))

    topTweetsThisMinute.foreachRDD(rdd => {
      val topList = rdd.take(10)
      if(topList.length>0) {
        println(Console.CYAN + "\nPopular topics in last 60 seconds (%s total):".format(rdd.count()))
        topList.foreach { case (count, tag) => println("%s (%s tweets)".format(tag, count))}
        println(Console.BLACK)
      }
    })

    ssc.start()
    ssc.awaitTermination()
  }

}
