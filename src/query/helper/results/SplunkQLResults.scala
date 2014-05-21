package query.traits

import scala.util.parsing.json.JSON
import scala.util.parsing.json.JSONObject

import org.joda.time.DateTime
import org.joda.time.Days

import query.BaseTimeSpan
import query.property.QueryElementToJSON
import query.property.SensisQueryElement

object SplunkQLResults {

  def queryTrends(t: Int, b: Int, e: Int, p: SensisQueryElement, r: String*): JSONObject = {
    QueryElementToJSON(queryDataTrend(t, b, e, p, r.toArray).sortWith(_.getProperty[Int]("times") > _.getProperty[Int]("times")))
  }

  def hotQuery(jsonStr: JSONObject): Map[String, AnyRef] = {
    (JSON.parseFull(jsonStr.toString)) match {
      case Some(e) => {
        val jsonItems = (e.asInstanceOf[Map[String, Any]]).get("items")
        jsonItems match {
          case Some(j: List[Map[String, AnyRef]]) => {
            val sortedList: List[Map[String, AnyRef]] = j.sortWith((x, y) => x.getOrElse("trend", 0.0).asInstanceOf[Double] > y.getOrElse("trend", 0.0).asInstanceOf[Double])
            sortedList.head
          }
        }
      }
      case None => null
    }
  }

  def coldQuery(jsonStr: JSONObject): Map[String, AnyRef] = {
    (JSON.parseFull(jsonStr.toString)) match {
      case Some(e) => {
        val jsonItems = (e.asInstanceOf[Map[String, Any]]).get("items")
        jsonItems match {
          case Some(j: List[Map[String, AnyRef]]) => {
            val sortedList: List[Map[String, AnyRef]] = j.sortWith((x, y) => x.getOrElse("trend", 0.0).asInstanceOf[Double] < y.getOrElse("trend", 0.0).asInstanceOf[Double])
            sortedList.head
          }
        }
      }
      case None => null
    }
  }

  def queryDataTrend(t: Int, b: Int, e: Int, p: SensisQueryElement, r: Array[String]): List[SensisQueryElement] = {

    def getDiffPercentage(curr: Double, old: Double): Double = {
      val a: Double = ((curr - old) / old) * 100
      a
    }

    def getIntDays(currDate: DateTime, d: Int): Int = Days.daysBetween(new DateTime(BaseTimeSpan.base),
      new DateTime().withDate(currDate.getYear(), currDate.getMonthOfYear(), 01).minusMonths(d))
      .getDays()

    var start: List[SensisQueryElement] = null
    var end: List[SensisQueryElement] = null
    if (b == 0 && e == 0) {
      val beginDate: Int = getIntDays(DateTime.now(), 1)
      start = (SplunkQLQuery.getQueryAsList(t, beginDate, (getIntDays(DateTime.now(), 0) - 1), p, Array("times"))).sortWith(_.getProperty[Int]("times") > _.getProperty[Int]("times"))
      end = (SplunkQLQuery.getQueryAsList(t, getIntDays(DateTime.now(), 2), (beginDate - 1), p, Array("times"))).sortWith(_.getProperty[Int]("times") > _.getProperty[Int]("times"))

    } else if (b != 0 && e == 0) {
      start = (SplunkQLQuery.getQueryAsList(t, b, (b + 7), p, Array("times"))).sortWith(_.getProperty[Int]("times") > _.getProperty[Int]("times"))
      end = (SplunkQLQuery.getQueryAsList(t, (b - 7), b, p, Array("times"))).sortWith(_.getProperty[Int]("times") > _.getProperty[Int]("times"))

    }

    var queryTrendList: List[SensisQueryElement] = List.empty
    start.foreach(x => {
      val newSQE = new SensisQueryElement
      newSQE.args = x.args

      end.find(k => (k.getProperty[String]("query").equals(x.getProperty[String]("query"))) && (k.getProperty[String]("location").equals(x.getProperty[String]("location")))) match {

        case Some(e: SensisQueryElement) => newSQE.insertProperty("trend", getDiffPercentage(x.getProperty[Int]("times"), e.getProperty[Int]("times")))
        case None => newSQE.insertProperty("trend", 100.00)
      }
      queryTrendList = newSQE :: queryTrendList
    })
    queryTrendList
  }

}