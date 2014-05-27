package searchQuality

import query.property.SensisQueryElement
import com.mongodb.casbah.Imports._
import query._
import scala.util.parsing.json.JSONObject
import query.property.QueryElementToJSON
import cache.SearchQualityDBName
import scala.util.parsing.json.JSONObject

object SearchQualityQuery extends SearchQualityQryTrait {

  def query(days: Int, sqe: SensisQueryElement, arr: String*): JSONObject = {
    QueryElementToJSON((queryRecord(days, sqe, arr.toArray)).toList)
  }

  def compare(b: Int, e: Int, sqe: SensisQueryElement, arr: String*): JSONObject = {
    QueryElementToJSON(compareBase(b, e, sqe, arr.toArray))
  }

  private def queryAsSensisQueryElem(args: Array[String]): MongoDBObject => SensisQueryElement = x => {
    val reVal = new SensisQueryElement
    reVal.insertProperty("days", x.getAsOrElse[Int]("days", 0))
    for (it <- args) {
      if (it != "days")
        reVal.insertProperty(it, x.getAsOrElse(it, ""))
    }
    reVal
  }

  private def queryRecord(days: Int, sqe: SensisQueryElement, arr: Array[String]): IQueryable[SensisQueryElement] = {

    var fl: Array[String] = null
    if (arr.length == 1)
      fl = Array("days", "SAPI", "SAPI_Comment", "Yellow", "Yellow_Comment", "One_Search", "One_Search_Comment")
    else
      fl = arr.toArray

    val dataRecord = from db () in SearchQualityDBName.search_quality_data select queryAsSensisQueryElem(fl)
    dataRecord
  }

  private def compareBase(b: Int, e: Int, sqe: SensisQueryElement, arr: Array[String]): List[SensisQueryElement] = {

    var left: SensisQueryElement = null
    var right: SensisQueryElement = null

    if (b == 0 && e == 0) {
      val recordsList = (queryRecord(0, sqe, arr).orderbyDecsending(x => x.getProperty[Int]("days")).top(2)).toList

      if (recordsList(0).getProperty[Int]("days") > recordsList(1).getProperty[Int]("days")) {
        left = recordsList(1)
        right = recordsList(0)
      } else {
        left = recordsList(0)
        right = recordsList(1)
      }

      var result: SensisQueryElement = new SensisQueryElement
      result.insertProperty("SAPI_curr", (right.getProperty[String]("SAPI")).toDouble)
      result.insertProperty("SAPI_prev", (left.getProperty[String]("SAPI")).toDouble)
      result.insertProperty("SAPI_change", ((right.getProperty[String]("SAPI")).toDouble - left.getProperty[String]("SAPI").toDouble))
      result.insertProperty("SAPI_comment", (right.getProperty[String]("SAPI")).toDouble)

      result.insertProperty("Yellow_curr", (right.getProperty[String]("Yellow")).toDouble)
      result.insertProperty("Yellow_prev", (left.getProperty[String]("Yellow")).toDouble)
      result.insertProperty("Yellow_change", ((right.getProperty[String]("Yellow")).toDouble - left.getProperty[String]("Yellow").toDouble))
      result.insertProperty("Yellow_comment", (right.getProperty[String]("SAPI")).toDouble)

      result.insertProperty("one_curr", (right.getProperty[String]("One_Search")).toDouble)
      result.insertProperty("one_prev", (left.getProperty[String]("One_Search")).toDouble)
      result.insertProperty("one_change", ((right.getProperty[String]("One_Search")).toDouble - left.getProperty[String]("One_Search").toDouble))
      result.insertProperty("one_comment", (right.getProperty[String]("SAPI")).toDouble)

      result :: List.empty[SensisQueryElement]
    } else
      null
  }

}