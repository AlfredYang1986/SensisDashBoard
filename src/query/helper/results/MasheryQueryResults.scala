package query.helper.results

import java.text.SimpleDateFormat

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