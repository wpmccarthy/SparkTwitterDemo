/**
 * Created by Will on 02/09/2014.
 */

import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.twitter._
import com.datastax.spark.connector.streaming._
import com.github.nscala_time.time.Imports._


object LiveTweetSearch extends StreamsWithCassandra{

  def main(args: Array[String]): Unit ={

    TwitterCredentials.setCredentials()

    val streamingContext = createStreamingContext(2)

    println("Getting tweets...")

    val twitterStream = TwitterUtils.createStream(streamingContext, None)

    val tweetTexts = twitterStream.map(_.getText)

    //val tagsAndText = tweetTexts.flatMap(line => line.split(" ").filter(_.startsWith("#")).map((_,DateTime.now.millis(0),line)))

    //tagsAndText.saveToCassandra("twitter", "tweets_by_tags", Seq("hashtag", "time_tweeted", "text"))

    val searchHits = tweetTexts.filter(_.contains(" ")).map((1,_))

    tweetTexts.foreachRDD(rdd => {println("RDD"); rdd.foreach( status => println(Console.GREEN + status + Console.BLACK))})

    streamingContext.start()
    streamingContext.awaitTermination()
  }

}
