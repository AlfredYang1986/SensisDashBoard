/**
 * one day data insert once
 * Created by Alfred Yang
 * 14th May, 2014
 */

package cache

import com.mongodb.casbah.Imports._
import query._

object SplunkQueryLocationCache extends SplunkCache {
	var days : Int = 0
	var ql : Map[String, DBObject] = Map.empty

	def initCache = clearCache
	def clearCache = ql = Map.empty
	def synchonaizeCache = {
		val database = SplunkDatabaseName.splunk_query_data.format(days)
		if (!isClean) {
			// cache all the one day data in one day 
			var it = ql.iterator
			while (it.hasNext) {
				val (key, value) = it.next
				_data_connection.getCollection(database) += (MongoDBObject("days" -> days) ++ value)
			}
		}
	}
	def isClean : Boolean = ql.isEmpty
	def addRecord(d : Int, q : String, l : String) = {
		def unionDBObject(old : DBObject) : DBObject = {
	  	    val bd = MongoDBObject.newBuilder

			var times = old.getAsOrElse("times", 0)
			bd += ("times" -> (times + 1))

			(old ++ bd.result)
	  	}
	  	def newDBObject : DBObject = MongoDBObject("query" -> q, "location" -> l, "times" -> 1)
	  
	  	if (isClean) days = d
	  	
	  	if (days != d) {
	  		synchonaizeCache
	  		clearCache
	  		days = d
	  	}
	  
	  	ql.get(q + l) match {
	  	  case Some(e) => ql += (q + l -> unionDBObject(e))
	  	  case none => ql += (q + l -> newDBObject)
	  	}	
	}
}