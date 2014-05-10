package query.helper.results

import java.text.SimpleDateFormat

import scala.collection.immutable.Map
import scala.collection.immutable.TreeMap
import scala.util.parsing.json.JSONObject

import org.joda.time.DateTime
import org.joda.time.Days

import com.mongodb.casbah.Imports.mongoQueryStatements

import query.BaseTimeSpan
import query.from
import query.helper.SplunkHelper
import query.property.SensisQueryElement

/*
 * TODO: Stub class to handle splunk data collection.
 */
class SplunkQueryResults {

  /**
   * Retrieve a single user object with the function calls summed-up.
   */
  def getEachUserByKey(startDate: String, endDate: String, logSourceName: String, ukey: String): List[SensisQueryElement] = {
    val start = getIntDays(startDate)
    val end = getIntDays(endDate)
    var q_data = from db () in "splunkdata" where (SplunkHelper.queryBetweenTimespanDB(start, end), SplunkHelper.queryByUserKeyDB(ukey)) select
      SplunkHelper.querySplunkDBOToQueryObject(List[String]("search", "getListingById"))

    q_data.toList
  }

  /**
   * Retrieve a list of distinct users keys.
   */
  def getAllDistinctUserKeys(startDate: String, endDate: String, logSourceName: String): List[String] = {
    null
  }

  /**
   * Retrieve distinct users with the function calls summed-up.
   *
   * @param startDate	Start date of the range
   * @param endDate	End date of the range
   * @param logSourceName
   * @param top	Get number of top users based on "search" end-point.
   * @return	A list of SensisQueryElements
   */
  def getDistinctUsers(startDate: String, endDate: String, logSourceName: String, top: Int): List[SensisQueryElement] = {
    val start = getIntDays(startDate)
    val end = getIntDays(endDate)

    var q_data = from db () in "splunkdata" where (SplunkHelper.queryBetweenTimespanDB(start, end)) select
      SplunkHelper.querySplunkDBOToQueryObject(List[String]("search", "getByListingId"))
    val distinctUsers = q_data.aggregate(
      SplunkHelper.AggregateByProperty("key"),
      SplunkHelper.AggregateSumSplunkData(List[String]("search", "getByListingId")))
      .orderbyDecsending(x => { x.getProperty[Int]("search") })

    if (top == 0)
      distinctUsers.toList
    else
      distinctUsers.top(top).toList
  }

  /**
   * Provide the function usage for each distinct function, in descending order.
   */
  def getFunctionUsage(startDate: String, endDate: String, logSourceName: String) {    
    var sumWithDateMap: TreeMap[String, Map[String, Int]] = TreeMap.empty
    val queryData = from db () in "splunkdata" where (SplunkHelper.queryBetweenTimespanDB(getIntDays(startDate), getIntDays(endDate))) select
      SplunkHelper.querySplunkDBOWithDays(List[String]("days", "search", "getByListingId"))
    val distinctRecords = queryData.aggregate(
      SplunkHelper.AggregateByProperty("days"),
      SplunkHelper.AggregateSumSplunkData(List[String]("days","search", "getByListingId")))
      .orderbyDecsending(x => { x.getProperty[Int]("search") })

    for (record <- distinctRecords) {
      var propMap: Map[String, Int] = Map.empty
      var date: String = ""
      record.args.foreach(x => {
        println(x)
        if (!(x.name).equals("days") && !((x.name).equals("key"))) propMap += (x.name -> (x.get).##)
        else date = x.get.toString
      })
      sumWithDateMap += (date -> propMap)
    }
    sumWithDateMap.foreach(println)
  }

  def getIntDays(dateStr: String): Int = {
    var givenDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateStr)
    givenDate = new SimpleDateFormat("dd/MMM/yyyy").parse(new SimpleDateFormat("dd/MMM/yyyy").format(givenDate))
    val daysInRange: Int = Days.daysBetween(new DateTime(BaseTimeSpan.base), new DateTime(givenDate)).getDays()
    daysInRange
  }

  def getQueryOccurances(queryStr: String, queryType: String): Map[String, List[SensisQueryElement]]= {
//  def getQueryOccurances(queryStr: String, queryType: String): JSONObject = {
    val qWords: Array[String] = queryStr.split(" ")
    var dataMap: Map[String, List[SensisQueryElement]] = Map.empty

    for (word <- qWords) {
      if (word != "") {
        val queryData = from db () in "splunk_query_data" where (
          if ((queryType.toLowerCase.trim).equals("query"))
            queryType.toLowerCase.trim $eq word.trim.toLowerCase
          else
            queryType.toLowerCase.trim $regex word.trim.toLowerCase) select SplunkHelper.getSplunkQueriesToObject("query", "location", "occurances")

        dataMap += (word -> queryData.toList)
      }
    }
//    new JSONObject(dataMap)
    var reVal : List[SensisQueryElement] = Nil
    for (t <- dataMap) reVal = reVal ::: t._2
    var m : Map[String, List[SensisQueryElement]] = Map.empty
    m += ("items" -> reVal)
    m
  }
}