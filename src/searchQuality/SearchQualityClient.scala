/**
 * Functions for insert, update and delete search quality records. *
 */
package searchQuality

import com.mongodb.casbah.Imports._
import query._
import com.mongodb.casbah.commons.MongoDBObject
import cache.SearchQualityDBName
import query.property.SensisQueryElement
import query.property.QueryElementToJSON
import scala.util.parsing.json.JSONObject

object SearchQualityClient {

  /**
   * @param dataMap - Data inserted via interface, as key-value pairs
   * @return a boolean that indicate status of insert or update
   */
  def add(days: Int, dataMap: Map[String, Any]): Boolean = {
    addOrUpdate(days, dataMap)
  }

  /**
   * @param dataMap - Data inserted via interface, as key-value pairs
   * @return
   */
  def delete(days: Int, sqe: SensisQueryElement): Boolean = {
    deleteRecord(days, sqe)
  }

  private def addOrUpdate(days: Int, dataMap: Map[String, Any]): Boolean = {
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

  private def deleteRecord(days: Int, sqe: SensisQueryElement): Boolean = {
    def getCollection = {
      if (sqe.getProperty[String]("collection").equalsIgnoreCase("eval")) _data_connection.getCollection(SearchQualityDBName.evaluation_matric_data)
      else _data_connection.getCollection(SearchQualityDBName.search_quality_data)
    }

    if ((sqe != null) && (sqe.getProperty[String]("collection") != null)) {
      try {
        getCollection.remove(MongoDBObject("days" -> days))
        true
      } catch {
        case e: MongoException => false
      }
    } else false
  }
}