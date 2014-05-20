package unit_test

import query._
import query.helper._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query.dsl.QueryExpressionObject
import query.property.QueryElementToJSON
import query.traits._
import query.property.SensisQueryElement

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

	val a = new SensisQueryElement
	a.insertProperty("yello", true)
	val b = new SensisQueryElement
	b.insertProperty("yello", false)
	val query_a = SplunkQuery.query(10298, 10305, a, "search", "getByListingId")
	val query_b = SplunkQuery.query(10298, 10305, b, "search", "getByListingId")
	val query_0 = SplunkQuery.query(10298, 10305, new SensisQueryElement, "search", "getByListingId")
	println(query_a)
	println(query_b)
	println(query_0)
	
//	
//	val query_0 = SplunkQuery.query(10298, 10305, new SensisQueryElement, "search", "getByListingId")
//	val query_1 = SplunkQuery.query(10298, 10305, new SensisQueryElement, "*")
//	println(query_0)
//	println(query_1)
//
	val c = new SensisQueryElement
	c.insertProperty("query", "restaurants")
	val query_2 = SplunkQLQuery.queryTops(10, 10298, 10305, c, "*")
	println(query_2)
	val query_3 = SplunkQLQuery.queryTops(10, 10298, 10305, new SensisQueryElement, "*")
	println(query_3)
	
//	val d = new SensisQueryElement
//	d.insertProperty("query", "restaurants")
//	d.insertProperty("location", "all,states")
//	val q1 = SplunkQLQuery.queryTops(10, 10298, 10299, d, "*")
//	val q2 = SplunkQLQuery.queryTops(10, 10298, 10300, d, "*")
//	val q3 = SplunkQLQuery.queryTops(10, 10298, 10301, d, "*")
//	val q4 = SplunkQLQuery.queryTops(10, 10298, 10302, d, "*")
//	val q5 = SplunkQLQuery.queryTops(10, 10298, 10303, d, "*")
//	val q6 = SplunkQLQuery.queryTops(10, 10298, 10304, d, "*")
//
//	println(q1)
//	println(q2)
//	println(q3)
//	println(q4)
//	println(q5)
//	println(q6)
//
//	val a = "query" $in ("restaurants", "0427773052")
//	val b = $or( "query" $eq "restaurants", "location" $eq "all,states")
//	val c = "query" $eq "0427773052"
//	val d = $or( "query" $eq "restaurants", "query" $eq "wedding flowers")
//	val e = $and("query" $eq "wedding flowers", "location" $eq "gatton,qld")
//	val f = $or(c, e)
//	val q100 = from db() in "splunk_query_10298" where f select (x => x)
//	println(q100)
}
