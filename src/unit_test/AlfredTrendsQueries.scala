package unit_test

import query.traits.SplunkQLQuery
import query.property.SensisQueryElement
import query.traits.SplunkQueryHelper

object AlfredTrendsQueries extends App {
	var q_10298 = SplunkQLQuery.queryTopsWithQueryable(100, 10298, 10298, new SensisQueryElement, "*")
	var q_10299 = SplunkQLQuery.queryTopsWithQueryable(100, 10299, 10299, new SensisQueryElement, "*")
	var q_10300 = SplunkQLQuery.queryTopsWithQueryable(100, 10300, 10300, new SensisQueryElement, "*")
	var q_10301 = SplunkQLQuery.queryTopsWithQueryable(100, 10301, 10301, new SensisQueryElement, "*")
	var q_10302 = SplunkQLQuery.queryTopsWithQueryable(100, 10302, 10302, new SensisQueryElement, "*")
	var q_10303 = SplunkQLQuery.queryTopsWithQueryable(100, 10303, 10303, new SensisQueryElement, "*")
	var q_10304 = SplunkQLQuery.queryTopsWithQueryable(100, 10304, 10304, new SensisQueryElement, "*")
	var q_10305 = SplunkQLQuery.queryTopsWithQueryable(100, 10305, 10305, new SensisQueryElement, "*")

	q_10299 = SplunkQueryHelper.splunkQueryCompare(q_10298, q_10299, 100)
	q_10300 = SplunkQueryHelper.splunkQueryCompare(q_10299, q_10300, 100)
	q_10301 = SplunkQueryHelper.splunkQueryCompare(q_10300, q_10301, 100)
	q_10302 = SplunkQueryHelper.splunkQueryCompare(q_10301, q_10302, 100)
	q_10303 = SplunkQueryHelper.splunkQueryCompare(q_10302, q_10303, 100)
	q_10304 = SplunkQueryHelper.splunkQueryCompare(q_10303, q_10304, 100)
	q_10305 = SplunkQueryHelper.splunkQueryCompare(q_10304, q_10305, 100)

	println(q_10298.top(10))
	println(q_10299.top(10))
	println(q_10300.top(10))
	println(q_10301.top(10))
	println(q_10302.top(10))
	println(q_10303.top(10))
	println(q_10304.top(10))
	println(q_10305.top(10))
	
	q_10299 = SplunkQueryHelper.splunkQueryHotQuery(q_10299)
	println(q_10299.top(10))
	q_10300 = SplunkQueryHelper.splunkQueryColdQuery(q_10300)
	println(q_10300.top(10))
}