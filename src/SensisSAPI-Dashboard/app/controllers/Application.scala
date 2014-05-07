package controllers

import play.api.mvc.{Action, Controller}
import query._
import query.helper._
import play.api.libs.json._
import scala.util.parsing.json.JSONObject
import org.joda.time.Days
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import query.property.SensisQueryElement
import com.mongodb.casbah.Imports.mongoQueryStatements
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

import scala.collection.immutable.TreeMap

object Application extends Controller {
  
  def index = Action {
    
    val users = from db() in "splunkdata" select 
				SplunkHelper.querySplunkDBOToQueryObject("key")
				
	val f = users.distinctBy(x => x.getProperty[String]("key")).toList
	val d = getUserCountForDateRange("2008","2014").toMap
    Ok(views.html.index("Hello Play Framework",f,d))
  }
  
    def getSplunkUserByDate(s:String, e:String, k:String) = Action {   
    val queryName : List[String] = List("search","getByListingId", "serviceArea", "index/listingsInHeadingInLocality", "singleSearch", "report/appearance", "report/viewDetails", "index/topCategoriesInLocality", "index/localitiesInState")
    val start = Days.daysBetween(new DateTime(BaseTimeSpan.base),
        new DateTime(new SimpleDateFormat("MM/dd/yyyy").parse(s))).getDays()
    val end = Days.daysBetween(new DateTime(BaseTimeSpan.base),
        new DateTime(new SimpleDateFormat("MM/dd/yyyy").parse(e))).getDays()
    val query_time_name = from db() in "splunkdata" where 
    				  	  SplunkHelper.queryByUserKeyBetweenTimespanDB(k, start, end) select
					  	  SplunkHelper.querySplunkDBOToQueryObject(queryName)
    val user = query_time_name.aggregate(SplunkHelper.AggregateByProperty("key"), SplunkHelper.AggregateSumSplunkData(queryName))
    val query_001 = user.top(1)
	var m1 : Map[String, Any] = Map.empty
    query_001.toList.map(x=>x.args.map{ y =>
        if (y.name.contains("/")) m1 += (y.name.substring(y.name.indexOf('/') + 1) ->y.get)
        else m1 +=(y.name -> y.get)    
    }) 
    Ok(new JSONObject(m1).toString())
  }
  
  def getSplunkUserOverviewData(k: String) = Action{
  val userKey = k;  
  val queryName : List[String] = List("search","getByListingId", "serviceArea", "index/listingsInHeadingInLocality", "singleSearch", "report/appearance", "report/viewDetails", "index/topCategoriesInLocality", "index/localitiesInState")
  val query = from db() in "splunkdata" where 
				SplunkHelper.queryByUserKeyDB(userKey) select
				SplunkHelper.querySplunkDBOToQueryObject(queryName)
  val user = query.aggregate(SplunkHelper.AggregateByProperty("key"), SplunkHelper.AggregateSumSplunkData(queryName))
   val query_001 = user.top(1)
    var m1 : Map[String, Any] = Map.empty
    query_001.toList.map(x=>x.args.map{ y =>
        if (y.name.contains("/")) m1 += (y.name.substring(y.name.indexOf('/') + 1) ->y.get)
        else m1 +=(y.name -> y.get)    
    }) 
    Ok(new JSONObject(m1).toString())
  }
  
  
  def getEndPointSearch(queryStr: String, queryType: String) = Action {
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
    var reVal : List[SensisQueryElement] = Nil
    for (t <- dataMap) reVal = reVal ::: t._2

    Ok(reVal.size.toString())
  }
  
  def getUserCountForDateRange(startDate: String, endDate:String) = {    
    var userCountMap:TreeMap[String,Int] = TreeMap.empty
    val end: Date = new SimpleDateFormat("yyyy").parse(endDate)    
    val rangeCounter = Calendar.getInstance()
    rangeCounter.setTime(new SimpleDateFormat("yyyy").parse(startDate))    
        
    while ((rangeCounter.getTime()).getTime() <= end.getTime()){
      val rangeCounterStr = (new SimpleDateFormat("yyyy")).format(rangeCounter.getTime())
      
      val queryData = from db() in "masherydata" where ("created" $regex rangeCounterStr) select 
      MasheryHelper.queryMasheryDBOToQueryObject("email", "username")
      userCountMap += (rangeCounterStr -> queryData.count)
            
      rangeCounter.add(Calendar.YEAR, 1)
    }
    userCountMap
  }
  
}