/**
 * Created by Will on 02/09/2014.
 */

import org.apache.spark.streaming.twitter._
import com.datastax.spark.connector.streaming._
import com.github.nscala_time.time.Imports._

//This object gets tweets and data and saves to cassandra. Some of the functions (getUser for example) do not work as expected.

object FilteredTwitterStreamToCassandra extends StreamsWithCassandra {

  def main (args: Array[String]) {

    TwitterCredentials.setCredentials()

    val ssc = createStreamingContext(2)

    val stream = TwitterUtils.createStream(ssc, None)

    val tweets = stream.map { case (status) => (DateTime.now.second(0).millis(0), status.getUser.getName, status.getText)}

    println(Console.MAGENTA + "" + Console.BLACK)

    tweets.saveToCassandra("twitter", "tweetdata", Seq("date", "user", "text"))

    ssc.start()
    ssc.awaitTermination()

  }

}


