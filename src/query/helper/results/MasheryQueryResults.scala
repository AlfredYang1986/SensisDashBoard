package query.helper.results

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

import scala.collection.immutable.TreeMap
import scala.util.parsing.json.JSONObject

import org.joda.time.DateTime
import org.joda.time.Days

import com.mongodb.casbah.Imports.mongoQueryStatements

import query.BaseTimeSpan
import query.from
import query.helper.MasheryHelper
import query.property.SensisQueryElement

class MasheryQueryResults {

  def getUserCountForGivenDate(dateStr: String): Int = {
    val queryData = from db () in "masherydata" where ("created" $regex dateStr) select MasheryHelper.queryMasheryDBOToQueryObject("email", "username")
    
    queryData.count
  }
  
  /**
   *	Get user counts for a given date range.
   * 	@return	As a JSONObject {dateStr : count} 
   */
  def getUserCountForDateRange(startDate: String, endDate:String): List[SensisQueryElement] = {
     
    var userCountMap:TreeMap[String,Int] = TreeMap.empty
    val end: Date = new SimpleDateFormat("yyyy-MM-dd").parse(endDate)    
    val rangeCounter = Calendar.getInstance()
    rangeCounter.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(startDate))    
        
    while ((rangeCounter.getTime()).getTime() <= end.getTime()){
      val rangeCounterStr = (new SimpleDateFormat("yyyy-MM-dd")).format(rangeCounter.getTime())
      
      val queryData = from db() in "masherydata" where ("created" $regex rangeCounterStr) select 
      MasheryHelper.queryMasheryDBOToQueryObject("email", "username")
      userCountMap += (rangeCounterStr -> queryData.count)
            
      rangeCounter.add(Calendar.DATE, 1)
    }
//    new JSONObject(userCountMap)
    Nil
  }

  def getUserDetails(userKey: String): List[SensisQueryElement] = {
    val queryData = from db () in "masherydata" where ("username" -> userKey) select
      MasheryHelper.queryMasheryDBOToQueryObject("display_name", "first_name", "last_name", "username", "email",
        "area_status", "postal_code", "country_code", "created", "updated")
    queryData.toList.foreach(println)
    queryData.toList
  }

  def getIntDays(dateStr: String): Int = {
    val givenDate = (new SimpleDateFormat("dd/MMM/yyyy")).parse(dateStr)
    val daysInRange: Int = Days.daysBetween(new DateTime(BaseTimeSpan.base), new DateTime(givenDate)).getDays()
    daysInRange
  }

}