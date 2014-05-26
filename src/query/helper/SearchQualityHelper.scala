package query.helper

import cache.SplunkCacheFacade

class SearchQualityHelper {

  def insertData(dataMap: Map[String, Any]) {
    val cache = new SplunkCacheFacade

    for (it <- dataMap.keys) {
      if (it != "days")
        cache("searchQuality").addRecord(dataMap.getOrElse("days", 0).##, it, dataMap.get(it).toString)
    }
  }

}