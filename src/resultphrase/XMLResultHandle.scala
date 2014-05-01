package resultphrase

import scala.xml.XML._
import sensis.DBClient.DAO.User
import sensis.DBClient.DataHandlerFacade
import sensis.DBClient.DataBaseHandler
import java.text.SimpleDateFormat
import java.util.Date
import org.joda.time.Days
import org.joda.time.DateTime
import query._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query.dsl.QueryExpressionObject

object XMLResultHandle extends ResultHandle {
	def apply(result: String) = {
		def getUserKey(mp: Map[String, String]) : String = 
		  	mp.get("key") match{
		  		case Some(e) => e
		  	  	case none => "Unknown_User"
		  	}
		
		def getCallDays(date : String): Int = Days.daysBetween(new DateTime(BaseTimeSpan.base), 
		  	  		new DateTime(new SimpleDateFormat("dd/MMM/yyyy").parse(date))).getDays()
	 
		def getDateString(raw: String) : String = 
		  	raw.substring(raw.indexOf('[') + 1, raw.indexOf(']')).substring(0, raw.indexOf(' '))
		  	  	
		def getFunctionString(raw: String) : String = raw.substring(raw.indexOf('"'), raw.lastIndexOf('"'))
		
	  	println("Restoring Splunk Data ... ")
		(scala.xml.XML.loadString("<root>" + result.substring(result.indexOf("?>") + 2) + "</root>") \\ "result" \ "field"). map { field =>
			val k = (field \ "@k").text
		  	if (k == "_raw") {
		  	  	val str_date = getDateString(field.text)
		  	  	val days = getCallDays(str_date)
		  	  	val raw = getFunctionString(field.text)
		  	  	val method_name = RoutePhraseSplunk.phraseMethodName(raw)
		  	  	val temp = RoutePhraseSplunk.phraseArguments(raw)
		  	  	val user_key = getUserKey(temp)
		  	  	val query = from db() in "splunkdata" where ("days" $eq days, "key" $eq user_key) select (x=>x)
		  	  	if (query.empty) _data_connection.getCollection("splunkdata") += MongoDBObject("days" -> days, "key" -> user_key, method_name -> 1)
		  	  	else _data_connection.getCollection("splunkdata") update(query.fistOrDefault, RoutePhraseSplunk.addFunctionCalls(query.fistOrDefault, method_name))
		  	}

		}
	}
}
