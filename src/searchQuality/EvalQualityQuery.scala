package searchQuality

import query.property.SensisQueryElement
import com.mongodb.casbah.Imports._
import query._
import scala.util.parsing.json.JSONObject
import query.property.QueryElementToJSON
import cache.SearchQualityDBName
import scala.util.parsing.json.JSONObject

object EvalQualityQuery {

  def query(days: Int, sqe: SensisQueryElement, arr: String*): JSONObject = {
    QueryElementToJSON((queryRecord(days, sqe, arr.toArray)).toList)
  }
  
  def compare(sqe: SensisQueryElement, arr: Array[String]): JSONObject = ???
  
  private def queryAsSensisQueryElem(args: Array[String]): MongoDBObject => SensisQueryElement = x => {
    val reVal = new SensisQueryElement
    reVal.insertProperty("days", x.getAs[Int]("days").get)
    for (it <- args) {
      if (it != "days")
        reVal.insertProperty(it, x.getAsOrElse(it, ""))
    }
    reVal
  }
  
  private def queryRecord(days: Int, sqe: SensisQueryElement, arr: Array[String]): IQueryable[SensisQueryElement] = {

        var fl: Array[String] = null
    if (arr.length == 1)
      fl = Array("Name_Search", "Name_Search_Comment", "Type_Search", "Type_Search_Comment", "Concept_Recall", "Concept_Recall_Comment", "Duplicates,Duplicates_Comment", "Zero_Results", "Zero_Results_Comment")
    else
      fl = arr.toArray

    val dataRecord = from db () in SearchQualityDBName.evaluation_matric_data select queryAsSensisQueryElem(fl)
    dataRecord
  }
}