package unit_test

import jsondataparse._
import java.util.Date
import org.joda.time.Days
import org.joda.time.DateTime
import query._
import java.text.SimpleDateFormat

object JsonParseTest extends App {

	val test = """{"name": "Naoki",  "lang": ["Java", "Scala"]}"""
	val l3 = List("name", "lang")
	val l1 = List("lang")
	val l2 = List("name")
	
	println(parser.parse(test, l3))
	println(parser.parse(test, l2))
	println(parser.parse(test, l1))

	val s = "31/03/2014"
	val e = "01/04/2014"
	def getCallDays(date: String): Int = Days.daysBetween(new DateTime(BaseTimeSpan.base),
      new DateTime(new SimpleDateFormat("dd/MM/yyyy").parse(date))).getDays()
     
    println(getCallDays(s))
    println(getCallDays(e))
}