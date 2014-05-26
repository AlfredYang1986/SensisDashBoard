package cache

import com.mongodb.casbah.Imports._
import query._
import com.mongodb.casbah.commons.MongoDBObject

object SearchQualityCache extends SplunkCache {

  var days: Int = 0
  var counter: Int = 0
  var data: Map[String, DBObject] = Map.empty

  def initCache = clearCache

  def clearCache = data = Map.empty[String, DBObject]

  def isClean: Boolean = data.isEmpty

  def synchonaizeCache = {
    val dbCollection: String = SearchQualityDBName.search_quality_data
    val it = data.iterator

    while (it.hasNext) {
      val (key, value) = it.next
      _data_connection.getCollection(dbCollection) += (MongoDBObject("days" -> days) ++ value)
    }
  }

  def addRecord(d: Int, k: String, v: String) = {
    def unionDBObject(old: DBObject): DBObject = {
      var db = MongoDBObject.newBuilder
      db += (k -> v)
      (old ++ db.result)
    }

    def newDBObject: DBObject = MongoDBObject(k -> v)

    days = d

    data.get(d.toString) match {
      case Some(e) => {
        data += (d.toString -> unionDBObject(e))
        counter = counter + 1
      }
      case None => {
        data += (d.toString -> newDBObject)
        counter = counter + 1
      }
    }

    if (counter == 6) {
      synchonaizeCache
      clearCache
      counter = 0
    }
  }
}