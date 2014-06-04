package searchQuality

import scala.util.parsing.json.JSONObject
import com.mongodb.casbah.Imports._
import cache.SearchQualityDBName
import query._
import query.from
import query.helper.SplunkHelper
import query.property.QueryElementToJSON
import query.property.SensisQueryElement
import java.util.Calendar
import java.text.SimpleDateFormat

object SearchQualityQuery extends SearchQualityQryTrait {

  def query(days: Int, sqe: SensisQueryElement, arr: String*): JSONObject = {
    QueryElementToJSON((queryRecord(days, sqe, arr.toArray)).toList)
  }

  def compare(b: Int, e: Int, sqe: SensisQueryElement, arr: String*): JSONObject = {
    QueryElementToJSON(compareBase(b, e, sqe, arr.toArray))
  }

  def queryTop(days: Int, top: Int, sqe: SensisQueryElement, arr: String*): JSONObject = {
    val records = queryRecord(days, sqe, arr.toArray).orderbyDecsending(x => x.getProperty[Int]("days")).top(top)
    QueryElementToJSON(records.toList)
  }

  /**
   * Retrieves the quality percentages for SAPI, over the defined time.
   */
  def queryGrowth(b: Int, e: Int, sqe: SensisQueryElement, arr: String*): JSONObject = {
    JSONObject(getQualityGrowth(b, e, sqe, arr.toArray))
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
    var dataRecord: IQueryable[SensisQueryElement] = null

    var fl: Array[String] = null
    if (arr.length == 1)
      fl = SearchQualityDBName.search_quality_columns
    else
      fl = arr.toArray

    if (days == 0)
      dataRecord = from db () in SearchQualityDBName.search_quality_data select queryAsSensisQueryElem(fl)
    else
      dataRecord = from db () in SearchQualityDBName.search_quality_data where ("days" -> days) select queryAsSensisQueryElem(fl)

    dataRecord
  }

  private def compareBase(begin: Int, end: Int, sqe: SensisQueryElement, arr: Array[String]): List[SensisQueryElement] = {

    def getPreviousRecord(begin: Int) = {
      val records = from db () in SearchQualityDBName.search_quality_data where ("days" $lt begin) select queryAsSensisQueryElem(SearchQualityDBName.search_quality_columns)
      val prev = records.orderbyDecsending(x => x.getProperty[Int]("days")).top(1).toList

      if (prev.size >= 1) prev(0)
      else new SensisQueryElement
    }

    def calcChange(left: String, right: String) = {
      if (left.equals("") || right.equals(""))
        "Quality values undefined"
      else
        "%.2f".format(right.toDouble - left.toDouble)
    }

    def getComparison(left: SensisQueryElement, right: SensisQueryElement) = {
      var result: SensisQueryElement = new SensisQueryElement

      for (it <- right.args) {
        if (it.name.toLowerCase.contains("comment"))
          result.insertProperty(it.name, if (it.get.toString.equals("")) "Undefined" else it.get.toString)
        else if (it.name != "days") {
          result.insertProperty("%s_curr".format(it.name), if (it.get.toString.equals("")) "Undefined" else (it.get.toString).toDouble)
          result.insertProperty("%s_prev".format(it.name), if (left.getProperty(it.name).equals("")) "Undefined" else (left.getProperty(it.name).toString).toDouble)
          result.insertProperty("%s_change".format(it.name), calcChange(left.getProperty(it.name).toString, it.get.toString))
        }
      }
      result
    }

    if (begin == 0 && end == 0) { /* Default view */
      val recordsList = (queryRecord(0, sqe, arr).orderbyDecsending(x => x.getProperty[Int]("days")).top(2)).toList

      if (recordsList.size >= 2) {
        val result: SensisQueryElement = getComparison(recordsList(1), recordsList(0))
        result :: List.empty[SensisQueryElement]
      } else
        recordsList

    } else if (begin != 0 && end != 0) { /* When both time points defined */
      val left = queryRecord(begin, sqe, arr).toList
      val right = queryRecord(end, sqe, arr).toList

      if (left.size == 1 && right.size == 1) {
        val result: SensisQueryElement = getComparison(left(0), right(0))
        result :: List.empty[SensisQueryElement]
      } else
        right

    } else if (begin != 0 && end == 0) { /* Only one time point */
      val right = queryRecord(begin, sqe, arr).toList
      val left = getPreviousRecord(begin)

      if (right.size >= 1) getComparison(left, right(0)) :: List.empty[SensisQueryElement]
      else List.empty[SensisQueryElement]

    } else
      List.empty[SensisQueryElement]
  }

  /**
   * Retrieves the quality percentages for SAPI, over the defined time.
   */
  private def getQualityGrowth(begin: Int, end: Int, sqe: SensisQueryElement, arr: Array[String]): Map[String, Any] = {

    val sourceInput = sqe.getProperty[String]("source")
    if ((sourceInput != null) && !sourceInput.equals("")) {
      val fl: Array[String] = SearchQualityDBName.search_quality_columns
      val records = from db () in SearchQualityDBName.search_quality_data where SplunkHelper.queryBetweenTimespanDB(begin, end) select queryAsSensisQueryElem(fl)

      var dataMap: Map[String, Any] = Map.empty
      for (it <- records) {
        val cal = Calendar.getInstance()
	  	cal.setTime(BaseTimeSpan.base)
	  	cal.add(Calendar.DATE, (it.getProperty[Int]("days")))
	  	
        dataMap += (new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) -> it.getProperty[String]("%s".format(sourceInput)))
      }
      dataMap

    } else Map.empty[String, String]
  }

}
