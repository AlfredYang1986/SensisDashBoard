package query.helper.results

import query.property.SensisQueryElement
import query._
import org.joda.time.Days
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import query.helper.SplunkHelper
import com.mongodb.casbah.Imports.mongoQueryStatements
import scala.util.parsing.json.JSONObject
import scala.util.parsing.json.JSONObject

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
    var q_data = from db () in "spdatatest" where (SplunkHelper.queryBetweenTimespanDB(start, end), SplunkHelper.queryByUserKeyDB(ukey)) select
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

    var q_data = from db () in "spdatatest" where (SplunkHelper.queryBetweenTimespanDB(start, end)) select
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

  }

  def getIntDays(dateStr: String): Int = {
    val givenDate = new SimpleDateFormat("dd/MMM/yyyy").parse(dateStr)
    val daysInRange: Int = Days.daysBetween(new DateTime(BaseTimeSpan.base), new DateTime(givenDate)).getDays()
    daysInRange
  }

  def getQueryOccurances(queryStr: String, queryType: String): JSONObject = {
    val qWords: Array[String] = queryStr.split(" ")
    var dataMap: Map[String, List[SensisQueryElement]] = Map.empty

    for (word <- qWords) {
      if (word != "") {
        val queryData = from db () in "splunk_query_data" where (queryType.toLowerCase.trim $regex word.trim.toLowerCase) select
          SplunkHelper.getSplunkQueriesToObject("query", "location", "occurances")

        dataMap += (word -> queryData.toList)
      }
    }
    new JSONObject(dataMap)
  }
}