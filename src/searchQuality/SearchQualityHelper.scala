package searchQuality

object SearchQualityHelper {

  def insertOrUpdate(dataMap: Map[String, Any]): Boolean = {
    SearchQualityInsert.add(dataMap)
  }

  def deleteRecord(dataMap: Map[String, Any]): Boolean = SearchQualityInsert.delete(dataMap)

}