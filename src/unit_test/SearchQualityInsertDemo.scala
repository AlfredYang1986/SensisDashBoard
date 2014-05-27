package unit_test

//import searchQuality.SearchQualityHelper
import query.property.SensisQueryElement
import searchQuality.SearchQualityQuery
import searchQuality.SearchQualityClient

object SearchQualityInsertDemo extends App {

  val datamap: Map[String, Any] = Map("SAPI" -> "56.90", "SAPI_Comment" -> "bla.. bla..", "Yellow" -> "33.33", "Yellow_Comment" -> "more bla.. bla..", "One_Search" -> "12.00", "One_Search_Comment" -> "30.00")

  /* Add and Update demo*/
//  SearchQualityClient.add(10302, datamap)
  
//  SearchQualityHelper.deleteRecord(datamap)  
  
//  val sqe = new SensisQueryElement
//  sqe.insertProperty("collection", "search")
//  println(SearchQualityHelper.compare(10299, sqe))
  
  /* query demo*/
  println(SearchQualityQuery.query(0, new SensisQueryElement, "*"))
  println(SearchQualityQuery.compare(0, 0, new SensisQueryElement, "*"))
} 