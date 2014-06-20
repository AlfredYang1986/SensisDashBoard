package cache

import com.mongodb.casbah.Imports._
import query._

object SplunkRequestCache extends SplunkCache {
	var days : Int = 0
	var request : Map[String, Map[String, Int]] = Map.empty
	
	def initCache = clearCache
	def clearCache = request = Map.empty
	def synchonaizeCache = {
		val database = SplunkDatabaseName.splunk_request_data.format(days)
		if (_data_connection.isExisted(database)) _data_connection.resetCollection(database)
		
		if (!isClean) {
			// cache all the one day data in one day 
			var it = request.iterator
			while (it.hasNext) {
				val (key, value) = it.next
			
				var it_inner = value.iterator
				while (it_inner.hasNext) {
					val (key_inner, value_inner) = it_inner.next
					
					_data_connection.getCollection(database) += 
					  MongoDBObject("first" -> key, "secend" -> key_inner, "times" -> value_inner)
				}
			}
		}
	}
	def isClean : Boolean = request.isEmpty
	def addRecord(d : Int, q : String, l : String) = {
		def addRecordTimes(e : Map[String, Int], l : String) : Map[String, Int]= {
			e.get(l) match {
			  case Some(i : Int) => e + (l -> (i + 1))
			  case _ => e + (l -> 1)
			}
		}
	  
	  	if (isClean) days = d
	  	
	  	if (days != d) {
	  		synchonaizeCache
	  		clearCache
	  		days = d
	  	}
	  	
	  	request.get(q) match {
	  	  case Some(e : Map[String, Int]) => request = request + (q -> addRecordTimes(e, l));
	  	  case _ => request = request + (q -> addRecordTimes(Map.empty, l));
	  	}
	}
}
