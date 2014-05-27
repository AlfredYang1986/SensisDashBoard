package unit_test

import searchQuality.SearchQualityHelper

object SearchQualityInsertDemo extends App {

  val datamap: Map[String, Any] = Map("days" -> 10299, "SAPI" -> "56.90", "SAPI_comment" -> "bla.. bla..", "YELLOW" -> "33.33", "YELLOW_comment" -> "more bla.. bla..", "ONE_SEARCH" -> "81.00")

  // if update "true", else "false"
  SearchQualityHelper.insertOrUpdate(datamap)
  
//  SearchQualityHelper.deleteRecord(datamap)  
} 