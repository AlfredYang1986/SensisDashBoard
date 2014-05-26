package searchQuality

object SearchQualityHelper {

  def insertOrUpdate(dataMap: Map[String, Any], isUpdate: Boolean) {
    SearchQualityInsert.add(dataMap, isUpdate)
  }

  def deleteRecord(dataMap: Map[String, Any]) = SearchQualityInsert.delete(dataMap)

}