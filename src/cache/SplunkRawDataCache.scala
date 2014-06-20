/**
 * one day data insert once
 * Created by Alfred Yang
 * 14th May, 2014
 */

package cache

import com.mongodb.casbah.Imports._
import query._

object SplunkRawDataCache extends SplunkCache{
	val impl = new SplunkRawDataCacheImpl( () => synchonaizeCache )
	def initCache = clearCache
	def clearCache = impl.clearCache
	def synchonaizeCache = {
		val database = SplunkDatabaseName.splunk_raw_data.format(impl.days)
		_data_connection.getCollection(database).remove(MongoDBObject("yello" -> false))
		
		if (!isClean) {
			// cache all the one day data in one day 
			var it = impl.data.iterator
			while (it.hasNext) {
				val (key, value) = it.next
				_data_connection.getCollection(database) += (MongoDBObject("days" -> impl.days, "key" -> key, "yello" -> false) ++ value)
			}
		}
	}
	def isClean = impl.isClean
	def addRecord(d : Int, k : String, m : String) = impl.addRecord(d, k, m)
}
