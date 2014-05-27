package searchQuality

object SearchQualityHelper {

  def insertOrUpdate(dataMap: Map[String, Any]) {
    SearchQualityInsert.add(dataMap)
  }

  def deleteRecord(dataMap: Map[String, Any]) = SearchQualityInsert.delete(dataMap)

}