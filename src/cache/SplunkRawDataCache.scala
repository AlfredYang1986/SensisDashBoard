/**
 * one day data insert once
 * Created by Alfred Yang
 * 14th May, 2014
 */

package cache

import com.mongodb.casbah.Imports._
import query._

object SplunkRawDataCache extends SplunkCache{
	var days : Int = 0
	var data = Map.empty[String, DBObject]

	def initCache = clearCache
	def clearCache = data = Map.empty[String, DBObject]
	def synchonaizeCache = {
		val database = SplunkDatabaseName.splunk_raw_data.format(days)
		if (!isClean) {
			// cache all the one day data in one day 
			var it = data.iterator
			while (it.hasNext) {
				val (key, value) = it.next
				_data_connection.getCollection(database) += (MongoDBObject("days" -> days, "key" -> key) ++ value)
			}
		}
	}
	def isClean = data.isEmpty
	def addRecord(d : Int, k : String, m : String) = {
	  	def unionDBObject(old : DBObject) : DBObject = {
	  	    val bd = MongoDBObject.newBuilder

			var times = old.getAsOrElse(m, 0)
			bd += (m -> (times + 1))

			(old ++ bd.result)
	  	}
	  	def newDBObject : DBObject = MongoDBObject(m -> 1)
	  
	  	if (isClean) days = d
	  	
	  	if (days != d) {
	  		synchonaizeCache
	  		clearCache
	  		days = d
	  	}
	  
	  	data.get(k) match {
	  	  case Some(e) => data += (k -> unionDBObject(e))
	  	  case none => data += (k -> newDBObject)
	  	}
	}
}