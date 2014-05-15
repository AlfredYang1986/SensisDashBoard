/**
 * store splunk data to the database
 * Created By Alfred Yang
 */

package resultphrase

import scala.xml.XML._
import java.text.SimpleDateFormat
import java.util.Date
import org.joda.time.Days
import org.joda.time.DateTime
import query._
import com.mongodb.casbah.Imports._
import cache.SplunkCacheFacade

object SplunkResultHandle extends ResultHandle {
	val cache = new SplunkCacheFacade

	def apply(result: String) = {
		def start = {
			cache.cleanAll
			cache.initAll
		}
		def end = cache.synchonaizeAll
		def getUserKey(mp: Map[String, String]): String =
			mp.get("key") match {
			  case Some(e) => e
			  case none => "Unknown_User"
		}

		def getCallDays(date: String): Int = Days.daysBetween(new DateTime(BaseTimeSpan.base),
			new DateTime(new SimpleDateFormat("dd/MMM/yyyy").parse(date))).getDays()

		def getDateString(raw: String): String =
			raw.substring(raw.indexOf('[') + 1, raw.indexOf(']')).substring(0, raw.indexOf(' '))

		def getFunctionString(raw: String): String = raw.substring(raw.indexOf('"'), raw.lastIndexOf('"'))

		def phraseMethodName(s: String): String =
			if (s.contains('?')) s.substring(s.indexOf('/') + 1, s.indexOf('?'))
			else s.substring(s.indexOf('/') + 1, s.lastIndexOf(' '))

		def phraseArguments(s: String): Map[String, String] = {
			var re = Map.empty[String, String]
			if (!s.contains('?')) return re

			val sq = s.substring(s.indexOf('?') + 1, s.lastIndexOf(' '))
			val sl: List[String] = sq.split("&").toList
			for (it <- sl) {
				if (!it.isEmpty() && it.contains('=')) {
					re += (it.substring(0, it.indexOf('=')) -> it.substring(it.indexOf('=') + 1))
				}
			}
			re
		}

		def addFunctionCalls(left: MongoDBObject, right: String, yello: Int): DBObject = {
			val bd = MongoDBObject.newBuilder

			val method_name = right.replace('.', '_')
			var times = left.getAsOrElse(method_name, 0)
			bd += (method_name -> (times + 1))

			var oy = left.getAsOrElse("yello", 0)
			bd += ("yello" -> (oy + yello))

			(left ++ bd.result)
		}

		def fromYelloPage(mp: Map[String, String]): Int = {
			mp.get("source") match {
			  case Some(e) => if (e.toUpperCase == "YELLOW") 1 else 0
			  case none => 0
			}
		}
		
		def addQueryLocationPair(argsMap : Map[String, String], days : Int) = {
			def getUserQuery(): String =
				argsMap.get("query") match {
				  case Some(e) => URLArgDecoding.decoding(e.toString()).trim.toLowerCase
				  case none => ""
				}
			def getLocation(): String =
				argsMap.get("location") match {
				  case Some(e) =>
			  		  if (e.count(_ == '+') > 1) (e.replaceFirst("\\+", " ").replace('+', ',').trim).toLowerCase.toString
			  		  else (e.replace('+', ',').trim).toLowerCase.toString
				  case none => ""
				}
			
			cache("query").addRecord(days, getUserQuery, getLocation)
		}
		
		def performStore = {
			println("Storing Splunk Data ... ")
			(scala.xml.XML.loadString("<root>" + result.substring(result.indexOf("?>") + 2) + "</root>") \\ "result" \ "field").map { field =>
				val k = (field \ "@k").text
				if (k == "_raw") {
					val raw = getFunctionString(field.text)
					val days = getCallDays(getDateString(field.text))
					val mp = phraseArguments(raw)

					val user_key = getUserKey(mp)
					val method_name = phraseMethodName(raw).replace('.', '_')
					val nYello = fromYelloPage(mp)

					cache("raw").addRecord(days, user_key, method_name)
					cache("endpoint").addRecord(days, user_key, method_name)
				
					if (method_name.equals("search"))
						addQueryLocationPair(mp, days)
				}
			}
		}
		
		result match {
		  case "start" => start
		  case "end" => end
		  case _ => performStore
		}
	}
}
