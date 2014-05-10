package unit_test

import query.property.SensisQueryElement

object SQE_Test extends App {
	val a = new SensisQueryElement
	a.insertProperty("a", 1)
	a.insertProperty("b", 2)
	a.insertProperty("c", 3)
	a.insertProperty("d", "Yang")
	a.insertProperty("e", "Yuan")
	println(a)
	
	a.insertProperty("a", 5)
	println(a)
	
	println(a.getProperty("a"))
	println(a.getProperty("d"))
	println(a.getProperty("e"))
}