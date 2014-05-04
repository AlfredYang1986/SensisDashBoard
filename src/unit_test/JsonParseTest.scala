package unit_test

import jsondataparse._

object JsonParseTest extends App {

	val test = """{"name": "Naoki",  "lang": ["Java", "Scala"]}"""
	val l3 = List("name", "lang")
	val l1 = List("lang")
	val l2 = List("name")
	
	println(parser.parse(test, l3))
	println(parser.parse(test, l2))
	println(parser.parse(test, l1))
}