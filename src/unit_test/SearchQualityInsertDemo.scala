package unit_test

//import searchQuality.SearchQualityHelper
import query.property.SensisQueryElement
import searchQuality.SearchQualityQuery
import searchQuality.SearchQualityClient
import searchQuality.EvalQualityQuery

object SearchQualityInsertDemo extends App {

  val datamap: Map[String, Any] = Map("SAPI" -> "11.00", "SAPI_Comment" -> "bla.. bla..", "Yellow" -> "54.78", "Yellow_Comment" -> "more bla.. bla..", "One_Search" -> "48.55", "One_Search_Comment" -> "release failed")
  
  val evaldatamap: Map[String, Any] = Map("Name_Search"-> "", "Name_Search_Comment"-> "bla.. bla..", "Type_Search"-> "59.33", "Type_Search_Comment"-> "bla.. bla..", "Concept_Recall"-> "", "Concept_Recall_Comment"-> "bla.. bla..", "Duplicates"-> "12.33","Duplicates_Comment"-> "bla.. bla..", "Zero_Results"-> "89.33", "Zero_Results_Comment"-> "bla.. bla..")

  /* Add and Update demo*/
//  SearchQualityClient.add(10303, datamap)
//  SearchQualityClient.add(10301, evaldatamap)
 
  /* search query demo*/
//  println("queryyyyyyyyyyyyy   " + SearchQualityQuery.query(10302, new SensisQueryElement, "*"))
  println("commmmmmmmmm   " + SearchQualityQuery.compare(10303, 0, new SensisQueryElement, "*"))
  
  /* eval query demo*/
//  println("queryyyyyyyyyyyyy   " + EvalQualityQuery.query(10302, new SensisQueryElement, "*"))
//  println("commmmmmmmmm   " + EvalQualityQuery.compare(0, 0, new SensisQueryElement, "*"))
  
  /* Get quality growth */
//  val a = new SensisQueryElement
//  a.insertProperty("source", "SAPI")  
//  
//  println(SearchQualityQuery.queryGrowth(10299, 10301, a, "*"))
} 