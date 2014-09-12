/**
 * Created by Will on 02/09/2014.
 */

import com.datastax.spark.connector._

//simple demo to count the amount of tweets in a table, demonstrating the cassandraTable method.

object CountTweets extends ConnectsToCassandra{
  def countHashtags(keyspace: String, table: String){

    val sparkContext = connectToCassandra()

    val tweets = sparkContext.cassandraTable(keyspace, table)

    println(Console.CYAN + tweets.count() + " hashtags recorded in " + keyspace + "." + table + Console.BLACK)

  }
}