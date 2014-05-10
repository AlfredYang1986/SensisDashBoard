package query.helper

import query.property.SensisQueryElement
import com.mongodb.casbah.Imports._

object MasheryHelper {
  def queryByUsername(name: String): SensisQueryElement => Boolean = x => name == x.getProperty("username")

  def queryByUsernameDB(name: String): DBObject = ("username" $eq name)
 
  def queryMasheryDBOToQueryObject(args: String*): MongoDBObject => SensisQueryElement = x => {
    val reVal = new SensisQueryElement
    reVal.insertProperty("username", x.getAs[String]("username").get)
    for (it <- args) if (it != "nsername") x.get(it) match {
      case Some(e) => reVal.insertProperty(it, e)
      case None => ;
    }
    reVal
  }

  /**
   * Overloaded function to get a list of DB columns for data retrieval
   */
  def queryMasheryDBOToQueryObject(args: List[String]): MongoDBObject => SensisQueryElement = x => {
    val reVal = new SensisQueryElement
    reVal.insertProperty("username", x.getAs[String]("username").get)
    for (it <- args) if (it != "nsername") x.get(it) match {
      case Some(e) => reVal.insertProperty(it, e)
      case None => ;
    }
    reVal
  }
}