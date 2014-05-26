package searchQuality

import com.mongodb.casbah.Imports._
import query._
import com.mongodb.casbah.commons.MongoDBObject
import cache.SearchQualityDBName

object SearchQualityInsert {

  def add(dataMap: Map[String, Any], isUpdate: Boolean) = {
    addOrUpdate(dataMap, isUpdate)
  }

  def delete(dataMap: Map[String, Any]) = {
    deleteRecord(dataMap)
  }

  private def addOrUpdate(dataMap: Map[String, Any], isUpdate: Boolean) = {
    val days = dataMap.getOrElse("days", 0).##

    def getCollection = {
      if (dataMap.size > 7)
        _data_connection.getCollection(SearchQualityDBName.evaluation_matric_data)
      else
        _data_connection.getCollection(SearchQualityDBName.search_quality_data)
    }

    def getExistingRecord = getCollection.find(MongoDBObject("days" -> days))

    var db = MongoDBObject.newBuilder
    db += ("days" -> days)

    for ((key, value) <- dataMap) {
      if (key != "days")
        db += (key -> value)
    }

    if (isUpdate) {
      val oldRecord = getExistingRecord.one
      if (oldRecord != null) getCollection += (oldRecord ++ db.result)
    } else
      getCollection += db.result
  }

  private def deleteRecord(dataMap: Map[String, Any]) = {
    def getCollection = {
      if (dataMap.size > 7)
        _data_connection.getCollection(SearchQualityDBName.evaluation_matric_data)
      else
        _data_connection.getCollection(SearchQualityDBName.search_quality_data)
    }

    getCollection.remove(MongoDBObject("days" -> dataMap.getOrElse("days", 0).##))
  }
}