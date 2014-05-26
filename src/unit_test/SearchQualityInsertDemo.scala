package unit_test

import searchQuality.SearchQualityHelper

object SearchQualityInsertDemo extends App {

  val datamap: Map[String, Any] = Map("days" -> 10299, "SAPI" -> "67.90", "SAPI_comment" -> "bla.. bla..", "YELLOW" -> "33.33", "YELLOW_comment" -> "more bla.. bla..")

  // if update "true", else "false"
//  SearchQualityHelper.insertOrUpdate(datamap, false)
  
  SearchQualityHelper.deleteRecord(datamap)  
} 