package query.helper

import query.property.SensisQueryElement
import com.mongodb.casbah.Imports._

object SplunkHelper {
  def queryByUserKey(key: String): SensisQueryElement => Boolean = x => key == x.getProperty("key")

  def queryByUserKeyDB(key: String): DBObject = ("key" $eq key)

  def queryBetweenTimespan(st: Int, ed: Int): SensisQueryElement => Boolean = x =>
    x.getProperty[Int]("days") >= st && x.getProperty[Int]("days") <= ed

  def queryBetweenTimespanDB(st: Int, ed: Int): DBObject = ("days" $gte st $lte ed)

  def queryByUserKeyBetweenTimespan(key: String, st: Int, ed: Int): SensisQueryElement => Boolean =
    x => (key == x.getProperty("key")) && x.getProperty[Int]("days") >= st && x.getProperty[Int]("days") <= ed

  def queryByUserKeyBetweenTimespanDB(key: String, st: Int, ed: Int): DBObject = ("key" $eq key) ++ ("days" $gte st $lte ed)

  def discintByUserKey[T]: T => String = x => x match {
    case x: SensisQueryElement => x.getProperty[String]("key")
    case _ => ???
  }

  def AggregateByProperty[A, T](name: String): A => T = x => x match {
    case x: SensisQueryElement => x.getProperty(name)
    case _ => ???
  }

  def AggregateSumSplunkData(args: String*): List[SensisQueryElement] => SensisQueryElement = ls => {
    val reVal = new SensisQueryElement
    reVal.insertProperty("key", ls.head.getProperty("key"))
    for (it <- ls) //search = search + it.getProperty[Int]("search") 
      for (arg <- args)
        reVal.insertProperty(arg, reVal.getProperty[Int](arg) + it.getProperty[Int](arg))
    reVal
  }

  /**
   * Overloaded method to accept a list of String column names (end-point names)
   */
  def AggregateSumSplunkData(args: List[String]): List[SensisQueryElement] => SensisQueryElement = ls => {
    val reVal = new SensisQueryElement
    reVal.insertProperty("key", ls.head.getProperty("key"))
    for (it <- ls) //search = search + it.getProperty[Int]("search") 
      for (arg <- args)
        reVal.insertProperty(arg, reVal.getProperty[Int](arg) + it.getProperty[Int](arg))
    reVal
  }

  def querySplunkDBOToQueryObject(args: String*): MongoDBObject => SensisQueryElement = x => {
    val reVal = new SensisQueryElement
    reVal.insertProperty("key", x.getAs[String]("key").get)
    for (it <- args) {
      if (it != "key" && it != "days")
        reVal.insertProperty(it, x.getAsOrElse(it, 0))
    }
    reVal
  }

  /**
   * Overloaded method to accept a list of String column names (end-point names)
   */
  def querySplunkDBOToQueryObject(args: List[String]): MongoDBObject => SensisQueryElement = x => {
    val reVal = new SensisQueryElement
    reVal.insertProperty("key", x.getAs[String]("key").get)
    for (it <- args) {
      if (it != "key" && it != "days")
        reVal.insertProperty(it, x.getAsOrElse(it, 0))
    }
    reVal
  }

  /**
   * Retrieve records from "splunk_query_data" and generate SensisQueryElement.
   */
  def getSplunkQueriesToObject(args: String*): MongoDBObject => SensisQueryElement = x => {
    val reVal = new SensisQueryElement
    for (it <- args) {
      if (it != "occurances" && it != "days")
        reVal.insertProperty(it, x.getAsOrElse(it, ""))
      else
        reVal.insertProperty(it, x.getAsOrElse(it, 0))
    }
    reVal
  }

  /**
   * User information with the "days"
   */
  def querySplunkDBOWithDays(args: List[String]): MongoDBObject => SensisQueryElement = x => {
    val reVal = new SensisQueryElement
    reVal.insertProperty("key", x.getAs[String]("key").get)
    for (it <- args) {
      if (it != "key")
        reVal.insertProperty(it, x.getAsOrElse(it, 0))
    }
    reVal
  }

  /**
   * Sum function to get aggregation of non-Key collections
   */
  def AggregateSumQueryData(args: String*): List[SensisQueryElement] => SensisQueryElement = ls => {
    val reVal = new SensisQueryElement
        
    for (it <- ls){ //search = search + it.getProperty[Int]("search") 
      reVal.insertProperty("query", it.getProperty[String]("query"))
      reVal.insertProperty("location", it.getProperty[String]("location"))
      for (arg <- args) {
        if (arg != "query" && arg != "location")
          reVal.insertProperty(arg, reVal.getProperty[Int](arg) + it.getProperty[Int](arg))
      }
    }
    reVal
  }
}