package query.helper.results

import java.text.SimpleDateFormat

import scala.collection.immutable.TreeMap
import scala.collection.mutable.LinkedHashMap

import org.joda.time.DateTime
import org.joda.time.Days

import com.mongodb.casbah.Imports.IntDoNOk
import com.mongodb.casbah.Imports.mongoQueryStatements

import errorreport.Error_ArgumentNotFound
import query.BaseTimeSpan
import query.IQueryable
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
      var allEndPoints = from db () in "splunk_end_points" select { x => x.getAsOrElse[String]("function_name", "") }
      
      var q_data = from db () in "splunkdata" where (SplunkHelper.queryBetweenTimespanDB(start, end), SplunkHelper.queryByUserKeyDB(ukey)) select
        SplunkHelper.querySplunkDBOToQueryObject(allEndPoints)
  
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
      var allEndPoints = from db () in "splunk_end_points" select { x => x.getAsOrElse[String]("function_name", "") }
  
      var q_data = from db () in "splunkdata" where (SplunkHelper.queryBetweenTimespanDB(start, end)) select
        SplunkHelper.querySplunkDBOToQueryObject(allEndPoints)
      val distinctUsers = q_data.aggregate(
        SplunkHelper.AggregateByProperty("key"),
        SplunkHelper.AggregateSumSplunkData(allEndPoints))
        .orderbyDecsending(x => { x.getProperty[Int]("search") })
  
      if (top == 0)
        distinctUsers.toList
      else
        distinctUsers.top(top).toList
    }

  /**
   * Provide the function usage for each distinct function, in descending order.
   */
  def getFunctionUsage(startDate: String, endDate: String, isYellow: Boolean) {
    var sumWithDateMap: TreeMap[String, LinkedHashMap[String, Int]] = TreeMap.empty
    var allEndPoints = from db () in "splunk_end_points" select { x => x.getAsOrElse[String]("function_name", "") }

    val queryData = from db () in "splunkdata" where (SplunkHelper.queryBetweenTimespanDB(getIntDays(startDate), getIntDays(endDate))) select
      SplunkHelper.querySplunkDBOWithDays(allEndPoints :+ "days".toString())
    val distinctRecords = queryData.aggregate(
      SplunkHelper.AggregateByProperty("days"),
      SplunkHelper.AggregateSumEndPointData(allEndPoints))
      .orderbyDecsending(x => { x.getProperty[Int]("search") & x.getProperty[Int]("getByListingId") })

    for (record <- distinctRecords) {
      var propMap: LinkedHashMap[String, Int] = LinkedHashMap.empty
      var date: String = ""

      record.args.foreach(x => {
        println(x)
        if (!(x.name).equals("days")) propMap += (x.name -> (x.get).##)
        else date = x.get.toString
      })
      //     val a = propMap.toArray
      //     
      //      Sorting.quickSort(a)(Ordering[(Int)].on(x => (x._2)))
      //      for(t<-a.reverse)
      //        println(t)
      sumWithDateMap += (date -> propMap)
    }
    sumWithDateMap.foreach(println)
  }

  /**
   * Returns the number of days available from base, to the given date string.
   */
  def getIntDays(dateStr: String): Int = {
    var givenDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateStr)
    givenDate = new SimpleDateFormat("dd/MMM/yyyy").parse(new SimpleDateFormat("dd/MMM/yyyy").format(givenDate))
    val daysInRange: Int = Days.daysBetween(new DateTime(BaseTimeSpan.base), new DateTime(givenDate)).getDays()
    daysInRange
  }

  /**
   * Retrieves the query/location/occurrences sets for the given date range. (Each query occurrence per location)
   */
  def getQueryOccuranceBase(start: String, end: String): IQueryable[SensisQueryElement] = {
    val queryData = start match {
      case e: String => {
        if (end != null)
          from db () in "splunk_query_data" where (SplunkHelper.queryBetweenTimespanDB(getIntDays(start), getIntDays(end))) select SplunkHelper.getSplunkQueriesToObject("query", "location", "occurances")
        else
          from db () in "splunk_query_data" where ("days" $eq getIntDays(start)) select SplunkHelper.getSplunkQueriesToObject("query", "location", "occurances")
      }
      case none => throw Error_ArgumentNotFound
    }
    val distinctQueries = queryData.aggregate(
      SplunkHelper.AggregateByProperty("query"),
      SplunkHelper.AggregateSumQueryData("occurances")).orderby(x => { x.getProperty[String]("query") })

    distinctQueries
  }

  /**
   * Return the distinct queries as a list. 
   */
  def getQueryOccurances(start: String, end: String): List[SensisQueryElement] = {
    (getQueryOccuranceBase(start, end)).toList
  }

  /**
   * Return the top 10 distinct queries, as a list. 
   */
  def getTopTenQueries(start: String, end: String): List[SensisQueryElement] = {

    val topTenQueries = (getQueryOccuranceBase(start, end)).orderbyDecsending(x => { x.getProperty[Int]("occurances") }).top(10)
    topTenQueries.toList
  }

}