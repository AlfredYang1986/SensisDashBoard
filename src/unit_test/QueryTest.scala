package unit_test

import query.property.SensisQueryElement
import query.traits._
import query.from

object QueryTest extends App {
	val a = new SensisQueryElement
//	a.insertProperty("query", 0)
//	a.insertProperty("location", 0)
	
	(from db() in "splunk_query_10298").selectTop(10)("times")(x => println(x))
	
	var sr = SplunkQLQuery.queryTopsWithQueryable(100, 10298, 10298, a, "times")
	println(sr)
	var dr = SplunkQLQuery.queryTopsWithQueryable(100, 10299, 10299, a, "times")
	println(dr)
    var result = SplunkQueryHelper.splunkQueryCompare(sr, dr,SplunkQueryHelper.queryLocCondition(_, _), 500).top(10).toList
    println(result)
}
