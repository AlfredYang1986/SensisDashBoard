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
import query.property.SensisQueryElement

trait state {
	def name : String
	def move(t : String) : state
	def canMove(t : String) : Boolean
	def apply(result : String) : Unit
}

object none extends state {
	def name = "end"
	def move(t : String) = if (canMove(t)) { apply(t); initial } else this
	def canMove(t : String) = t == initial.name 
	def apply(result : String) : Unit = SplunkResultHandle.start
}

object initial extends state {
	def name = "start"
	def move(t : String) = if (canMove(t)) property else this
	def canMove(t : String) = t == property.name
	def apply(result : String) : Unit = {} // do nothing
}

object property extends state {
	def name = "property"
	def move(t : String) = if (canMove(t)) store else this
	def canMove(t : String) = t == store.name
	def apply(result : String) : Unit = SplunkResultHandle.saveKey(result)
}

object store extends state {
	def name = "store"
	def move(t : String) = if (canMove(t)) { SplunkResultHandle.end; none } else this 
	def canMove(t : String) = t == none.name
	def apply(result : String) : Unit = SplunkResultHandle.apply_acc(result)
}

object SplunkResultHandle extends ResultHandle {
	val cache = new SplunkCacheFacade
	var cur : state = none
	var special_key : String = null

	def start = {
		cache.cleanAll
		cache.initAll
	}
	def end = cache.synchonaizeAll("raw", "raw_yello", "endpoint", "query")

	def saveKey(k : String) = special_key = k
	
	def apply_acc(result : String) = {
		def getUserKey(mp: Map[String, String]): String =
			mp.get("key") match {
			  case Some(e) => if (e.contains(' ')) e.substring(0, e.indexOf(' ')).trim() else e
			  case none => "Unknown_User"
			}
		
		def getCallDays(date: String): Int = Days.daysBetween(new DateTime(BaseTimeSpan.base),
			new DateTime(new SimpleDateFormat("dd/MMM/yyyy").parse(date))).getDays()

		def getDateString(raw: String): String =
			raw.substring(raw.indexOf('[') + 1, raw.indexOf(']')).substring(0, raw.indexOf(' '))

		def getFunctionString(raw: String): String = raw.substring(raw.indexOf('"'), raw.lastIndexOf('"'))

		def phraseMethodName(s: String): String = {
			def endPointName(e : String) = if (e.contains('/')) e.substring(e.lastIndexOf('/') + 1) else e
		  
			if (s.contains('?')) endPointName(s.substring(s.indexOf('/') + 1, s.indexOf('?')))
			else endPointName(s.substring(s.indexOf('/') + 1, s.lastIndexOf(' ')))
		}

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

		def fromYelloPage(k : String) : String = if (k == special_key) "raw_yello" else "raw"
		  
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
		
		println("Storing Splunk Data ... ")
		(scala.xml.XML.loadString("<root>" + result.substring(result.indexOf("?>") + 2) + "</root>") \\ "result" \ "field").map { field =>
			val k = (field \ "@k").text
			if (k == "_raw") {
				val raw = getFunctionString(field.text)
				val days = getCallDays(getDateString(field.text))
				val mp = phraseArguments(raw)
				val user_key = getUserKey(mp)
				
				if (user_key != "Unknown_User"){
					val method_name = phraseMethodName(raw).replace('.', '_')
				
					cache(fromYelloPage(user_key)).addRecord(days, user_key, method_name)
					cache("endpoint").addRecord(days, user_key, method_name)
				
					if (method_name.equals("search"))
						addQueryLocationPair(mp, days)
				}
			}
		}
	}
	
	def apply(result: String) = {
		if (cur.canMove(result)) cur = cur.move(result)
		else cur.apply(result)
	}
}
