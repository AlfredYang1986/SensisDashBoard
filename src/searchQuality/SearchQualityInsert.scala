/**
 * Functions for insert, update and delete search quality records. *
 */
package searchQuality

import com.mongodb.casbah.Imports._
import query._
import com.mongodb.casbah.commons.MongoDBObject
import cache.SearchQualityDBName

object SearchQualityInsert {

  /**
   * @param dataMap - Data inserted via interface, as key-value pairs
   * @return a boolean that indicate status of insert or update
   */
  def add(dataMap: Map[String, Any]): Boolean = {
    addOrUpdate(dataMap)
  }

  /**
   * @param dataMap - Data inserted via interface, as key-value pairs
   * @return
   */
  def delete(dataMap: Map[String, Any]): Boolean = {
    deleteRecord(dataMap)
  }

  private def addOrUpdate(dataMap: Map[String, Any]): Boolean = {
    val days = dataMap.getOrElse("days", 0).##

    def getCollection = {
      if (dataMap.size > 7) _data_connection.getCollection(SearchQualityDBName.evaluation_matric_data)
      else _data_connection.getCollection(SearchQualityDBName.search_quality_data)
    }

    def getExistingRecord = getCollection.find(MongoDBObject("days" -> days))

    var db = MongoDBObject.newBuilder
    db += ("days" -> days)

    for ((key, value) <- dataMap) {
      if (key != "days")
        db += (key -> value)
    }

    val oldRecord = getExistingRecord.one
    try {
      if (oldRecord != null) { getCollection += (oldRecord ++ db.result); true }
      else { getCollection += db.result; true }
    } catch {
      case e: MongoException => false
    }
  }

  private def deleteRecord(dataMap: Map[String, Any]): Boolean = {
    def getCollection = {
      if (dataMap.size > 7) _data_connection.getCollection(SearchQualityDBName.evaluation_matric_data)
      else _data_connection.getCollection(SearchQualityDBName.search_quality_data)
    }

    try {
      getCollection.remove(MongoDBObject("days" -> dataMap.getOrElse("days", 0).##))
      true
    } catch {
      case e: MongoException => false
    }
  }
}