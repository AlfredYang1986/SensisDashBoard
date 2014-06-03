package unit_test

import query.traits.SplunkRequestQuery
import query.property.SensisQueryElement
import query.traits.SplunkQuery

object RequestLogTest extends App {
	val p = new SensisQueryElement
	p.insertProperty("first", "intent")
	var query = SplunkRequestQuery.query(10340, 10341, p, "times")
	println(query)
	
	var query1 = SplunkQuery.queryTops(100, 10290, 10310, new SensisQueryElement, "*")
	println(query1)
}