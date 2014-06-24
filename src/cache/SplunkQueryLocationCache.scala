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
	var qOnly : Map[String, DBObject] = Map.empty
	var lOnly : Map[String, DBObject] = Map.empty

	def initCache = clearCache
	def clearCache = { ql = Map.empty; qOnly = Map.empty; lOnly = Map.empty }
	def synchonaizeCache = {
		val database = SplunkDatabaseName.splunk_query_data.format(days)
		if (_data_connection.isExisted(database)) _data_connection.resetCollection(database)
		
		val query_only = SplunkDatabaseName.splunk_query_only_data.format(days)
		if (_data_connection.isExisted(database)) _data_connection.resetCollection(query_only)
		
		val location_only = SplunkDatabaseName.splunk_location_only_data.format(days)
		if (_data_connection.isExisted(database)) _data_connection.resetCollection(location_only)
		
		if (!isClean) {
			synchonaizeCache_acc(ql, database)
			synchonaizeCache_acc(qOnly, query_only)
			synchonaizeCache_acc(lOnly, location_only)
		}
	}
	
	private def synchonaizeCache_acc(m : Map[String, DBObject], database : String) = {
		// cache all the one day data in one day 
		var it = m.iterator
		while (it.hasNext) {
			val (key, value) = it.next
				if (value.getAsOrElse("times", 0) > 5)
					_data_connection.getCollection(database) += (MongoDBObject("days" -> days) ++ value)
		}
	}
	
	def isClean : Boolean = ql.isEmpty && qOnly.isEmpty && lOnly.isEmpty
	def addRecord(d : Int, q : String, l : String) = {
		def unionDBObject(old : DBObject) : DBObject = {
	  	    val bd = MongoDBObject.newBuilder

			var times = old.getAsOrElse("times", 0)
			bd += ("times" -> (times + 1))

			(old ++ bd.result)
	  	}
	  	def newDBObject : DBObject = MongoDBObject("query" -> q, "location" -> l, "times" -> 1)
	  	def newQueryOnlyDBObject : DBObject = MongoDBObject("query" -> q, "times" -> 1)
	  	def newLocationOnlyDBObject : DBObject = MongoDBObject("location" -> l, "times" -> 1)
	  
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
	  	qOnly.get(q) match {
	  	  case Some(e) => qOnly += (q -> unionDBObject(e))
	  	  case none => qOnly += (q -> newQueryOnlyDBObject)
	  	}	
	  	lOnly.get(l) match {
	  	  case Some(e) => lOnly += (l -> unionDBObject(e))
	  	  case none => lOnly += (l -> newLocationOnlyDBObject)
	  	}	
	}
}
