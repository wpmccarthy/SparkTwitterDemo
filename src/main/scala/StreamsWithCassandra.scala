/**
 * Created by Will on 02/09/2014.
 */

import org.apache.spark.streaming._

class StreamsWithCassandra extends ConnectsToCassandra{

  val sc = connectToCassandra()

  def createStreamingContext(batchDuration: Int): StreamingContext= {

    new StreamingContext(sc, Seconds(batchDuration))

  }
}
