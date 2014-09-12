/**
 * Created by Will on 02/09/2014.
 */
object TwitterCredentials{
  val consumerKey = "KEYKEYKEYKEYKEYKEYKEY"
  val consumerSecret = "KEYKEYKEYKEYKEYKEYKEYKEYKEYKEYKEYKEYKEYKEYKEYKEY"
  val accessToken = "KEYKEYKEY-KEYKEYKEYKEYKEYKEYKEYKEYKEYKEYKEYKEY"
  val accessTokenSecret = "KEYKEYKEYKEYKEYKEYKEYKEYKEYKEYKEYKEYKEYKEY"

  def setCredentials(): Unit =
  {
    // Set the system properties so that Twitter4j library used by twitter stream
    // can use them to generat OAuth credentials
    System.setProperty("twitter4j.oauth.consumerKey", consumerKey)
    System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret)
    System.setProperty("twitter4j.oauth.accessToken", accessToken)
    System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret)
  }
}