package unit_test

import query.property.SensisQueryElement
import query.traits._
import query.from

object QueryTest extends App {
	val a = new SensisQueryElement

	println(SplunkQLQuery.queryTopsWithQueryable(10, 10300, 10300, a, "*"))
	println(SplunkQLQuery.queryTopsWithQueryable(10, 10301, 10301, a, "*"))
	println(SplunkQLQuery.queryTopsWithQueryable(10, 10300, 10301, a, "*"))
}
