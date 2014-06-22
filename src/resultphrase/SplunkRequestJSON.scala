package resultphrase

import scala.util.parsing.json._
import scala.collection.immutable.HashMap
import cache.SplunkCacheFacade
import errorreport.Error_PhraseJosn
import org.joda.time.Days
import org.joda.time.DateTime
import query.BaseTimeSpan
import java.text.SimpleDateFormat

object SplunkRequestJSONHandle extends ResultHandle {
	val cache = new SplunkCacheFacade
	
	def start = {
		cache.cleanAll
		cache.initAll
	}
	def end = cache.synchonaizeAll("search_insignt")
	
	def apply_acc(result: String) = {
		def getCallDays(date: String): Int = Days.daysBetween(new DateTime(BaseTimeSpan.base), 
		    new DateTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(date))).getDays()
			
		def getJSONFormat(input : String) : Map[String, Any] = {
			JSON.parseFull(input.substring(input.indexOf("{"), input.lastIndexOf("}") + 1)) match {
				case Some(e) => e.asInstanceOf[Map[String, Any]]
				case None => throw Error_PhraseJosn
			}
		}

		def getDate(mp : Map[String, Any]) : String = {
			mp.get("date") match {
			  case Some(e : String) => e
			  case None => ""
			}
		}

		def addQueryDetailRecord(d : Int, which : String, mp : Map[String, Any]) = {
			mp.get(which) match {
			  case Some(e : String) => cache("search_insignt").addRecord(d, which, e)
			  case Some(e : Double) => cache("search_insignt").addRecord(d, which, e.toInt.toString)
			  case Some(e : Boolean) => cache("search_insignt").addRecord(d, which, e.toString)
			  case None => ; 
			}
		}
		
		def details = List( "intent", "granularity", "queryCount", "correctlySpelled", "metroExpansionDone", "conceptSource" )
		
		println("Storing Splunk Request Data ... ")
		(scala.xml.XML.loadString("<root>" + result.substring(result.indexOf("?>") + 2) + "</root>") \\ "result" \ "field").map { field =>
			val k = (field \ "@k").text
			if (k == "_raw") {
				val tmp = getJSONFormat(field.text)
				val days = getCallDays(getDate(tmp))
				for (it <- details) addQueryDetailRecord(days, it, tmp)
			}
		}
	}
	def apply(result: String) = result match {
	  case "start" => start
	  case "end" => end
	  case _ => apply_acc(result)
	}
}
