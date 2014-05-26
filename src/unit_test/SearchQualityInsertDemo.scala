package unit_test

import cache.SearchQualityCache
import cache.SplunkCacheFacade

object SearchQualityInsertDemo extends App {

  val datamap: Map[String, Any] = Map("days" -> 10299, "SAPI" -> "67.90", "SAPI_comment" -> "bla.. bla..", "YELLOW" -> "33.33", "YELLOW_comment" -> "more bla.. bla..")

  val cache = new SplunkCacheFacade

  for (it <- datamap.keys) {
    if (it != "days")
      cache("searchQuality").addRecord(datamap.getOrElse("days", 0).##, it, datamap.get(it).toString)
  }
} 