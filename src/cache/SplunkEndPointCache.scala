/**
 * one day data insert once
 * Created by Alfred Yang
 * 14th May, 2014
 */

package cache

import com.mongodb.casbah.Imports._
import query._

object SplunkEndPointCache extends SplunkCache {
	// endpoint
	var ed_list : List[String] = Nil
	def initCache = ed_list = (from db() in SplunkDatabaseName.splunk_end_point select (x => x.getAsOrElse("function_name", ""))).toList.distinct
	def addRecord(d : Int, k : String, ep : String) = if (!ed_list.contains(ep)) ed_list = ed_list :+ ep
	def synchonaizeCache = {
		ed_list.map { x => 
		  if (!(from db () in SplunkDatabaseName.splunk_end_point where ("function_name" -> x) contains))
		  		_data_connection.getCollection(SplunkDatabaseName.splunk_end_point) += MongoDBObject("function_name" -> x)
		  }
	}
	def isClean : Boolean = false
	def clearCache = ed_list = Nil
}