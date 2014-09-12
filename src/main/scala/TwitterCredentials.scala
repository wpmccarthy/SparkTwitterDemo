/**
 * Created by Will on 02/09/2014.
 */
object TwitterCredentials{
  val consumerKey = "pOzBeodg9bgeTAIjqX2vs4zhF"
  val consumerSecret = "hZoU6PbBfSLjONOn4HOiSj3hOrHVhl6t9tpx33Uik5JVPDAUPc"
  val accessToken = "23248795-8jhrzoq9L6hFomnqSA8HvnmXXUdWoOuuhDLBNzyHM"
  val accessTokenSecret = "AdUphjDCgwKTd4q0rjRKbmsBDIT9yGmE6NFuVZbadq9J6"

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