package unit_test

import query.property.SensisQueryElement
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.MongoDBObject
import query.traits.SplunkTermTrendQuery
import query.traits.SplunkLocationTrendQuery
import query.traits.SplunkQueryHelper

object SearchTermTest extends App {

// println(SplunkTermTrendQuery.queryTops(3, 10300, 10301, new SensisQueryElement, "times"))
//
// println(SplunkLocationTrendQuery.queryTopsWithQueryable(3, 10300, 10301, new SensisQueryElement, "times"))
//
  val a = SplunkLocationTrendQuery.queryTopsWithQueryable(3, 10300, 10301, new SensisQueryElement, "times")
  val b = SplunkLocationTrendQuery.queryTopsWithQueryable(3, 10300, 10301, new SensisQueryElement, "times")
  
  val a2 = SplunkTermTrendQuery.queryTopsWithQueryable(3, 10300, 10301, new SensisQueryElement, "times")
  val b2 = SplunkTermTrendQuery.queryTopsWithQueryable(3, 10300, 10301, new SensisQueryElement, "times")

// println(SplunkQueryHelper.splunkQueryCompare(a, b, 3))
//
// println(SplunkQueryHelper.splunkQueryCompare(a2, b2, 3))
  
  val qury = SplunkQueryHelper.splunkQueryCompare(a2, b2, SplunkQueryHelper.queryLocCondition(_, _), 3)
  println(qury)
  println(SplunkQueryHelper.splunkQueryHotQuery(qury))
}
