package unit_test

import jsondataparse._

object JsonParseTest extends App {

	val test = """{"name": "Naoki",  "lang": ["Java", "Scala"]}"""
	val p = new parser
	val l3 = List("name", "lang")
	val l1 = List("lang")
	val l2 = List("name")
	
	println(p.parse(test, l3))
	println(p.parse(test, l2))
	println(p.parse(test, l1))
}