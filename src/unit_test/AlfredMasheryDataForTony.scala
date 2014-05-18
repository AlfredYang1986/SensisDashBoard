package unit_test

import query._
import query.helper._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query.dsl.QueryExpressionObject
import query.property.QueryElementToJSON
import query.traits._
import query.property.SensisQueryElement
import java.util.Calendar
import java.text.SimpleDateFormat

object AlfredMasheryDataForTony extends App {
  //	val query = from db() in "masherydata" select
  //				MasheryHelper.queryMasheryDBOToQueryObject("first_name", "email")
  //	
  //	println(query)
  //	query.toList.map(x => x.args.map{ y => 
  //	  	println(y)
  //	})
  //	
  //	println(QueryElementToJSON(query.toList))

  //	val query = from db() in "10298_splunk_data" where ("days" -> 10300) select (x => x)
  //	val query = from db() in "splunk_data_10298" select (x => x)
  //	println(query.count)
  //	println(query)
  //	println
  //val query1 = from db() in "10298_splunk_query" select (x => x)
  //println(query1.top(100))

  //	val query_1 = from db() in "splunk_data_10298" select
  //					 SplunkHelper.querySplunkDBOToQueryObject("search")
  //	val query_2 = from db() in "splunk_data_10299" select
  //					 SplunkHelper.querySplunkDBOToQueryObject("search")
  //	val query_3 = from db() in "splunk_data_10300" select
  //					 SplunkHelper.querySplunkDBOToQueryObject("search")
  //	val query_4 = from db() in "splunk_data_10301" select
  //					 SplunkHelper.querySplunkDBOToQueryObject("search")
  //	val query_5 = from db() in "splunk_data_10302" select
  //					 SplunkHelper.querySplunkDBOToQueryObject("search")
  //	val query_6 = from db() in "splunk_data_10303" select
  //					 SplunkHelper.querySplunkDBOToQueryObject("search")
  //	val query_7 = from db() in "splunk_data_10304" select
  //					 SplunkHelper.querySplunkDBOToQueryObject("search")
  //	val query_8 = from db() in "splunk_data_10305" select
  //					 SplunkHelper.querySplunkDBOToQueryObject("search")
  //	println(query_1)
  //	println(query_2)
  //	println(query_3)
  //	println(query_4)
  //	println(query_5)
  //	println(query_6)
  //	println(query_7)
  //	println(query_8)

  val query_0 = SplunkQuery.query(10298, 10305, new SensisQueryElement, "search")
  val query_1 = SplunkQuery.query(10298, 10305, new SensisQueryElement, "*")
  println(query_0)
  println(query_1)

  val query_2 = SplunkQLQuery.queryTops(100, 10298, 10305, new SensisQueryElement, "*")
  println(query_2)

  val query_3 = MasheryQuery.query(9952, 10281, new SensisQueryElement, "*")
  val query_4 = MasheryQuery.queryCount(9952, 10281, new SensisQueryElement, "email")
  println(query_3)
  println(query_4)

  /* days check */
  val cal = Calendar.getInstance()
  cal.setTime(BaseTimeSpan.base)
  cal.add(Calendar.DATE, 10298)
  println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()))
}
