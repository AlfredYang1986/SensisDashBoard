package searchQuality

object SearchQualityHelper {

  def insertOrUpdate(days: Int, dataMap: Map[String, Any]): Boolean = {
    SearchQualityInsert.add(days, dataMap)
  }

  def deleteRecord(days: Int, dataMap: Map[String, Any]): Boolean = SearchQualityInsert.delete(days, dataMap)

}