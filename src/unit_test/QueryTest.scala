package unit_test

import query.property.SensisQueryElement
import query.traits._
import query.from

object QueryTest extends App {
//	val a = new SensisQueryElement
//
//	println(SplunkQLQuery.queryTopsWithQueryable(10, 10300, 10300, a, "*"))
//	println(SplunkQLQuery.queryTopsWithQueryable(10, 10301, 10301, a, "*"))
//	println(SplunkQLQuery.queryTopsWithQueryable(10, 10300, 10301, a, "*"))
  
	var sr = SplunkLocationTrendQuery.queryTopsWithQueryable(10, 10298, 10298, new SensisQueryElement, "times")
    var dr = SplunkLocationTrendQuery.queryTopsWithQueryable(10, 10299, 10299, new SensisQueryElement, "times")
    var result = SplunkQueryHelper.splunkQueryCompare(sr, dr,SplunkQueryHelper.queryLocCondition(_, _), 500).top(20).toList
    println(result)
}
