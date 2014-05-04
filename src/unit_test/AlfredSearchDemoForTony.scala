package unit_test

import query._
import query.helper._

object AlfredSearchDemoForTony extends App {
	// query by key 
	val query = from db() in "splunkdata" where 
				SplunkHelper.queryByUserKeyDB("66ad29c2c88ecb081ec7cca125960528") select
				SplunkHelper.querySplunkDBOToQueryObject("search")
	
	println(query)
	println(query.aggregate(
	    SplunkHelper.AggregateByProperty("key"), 
	    SplunkHelper.AggregateSumSplunkData("search")))
	    
	// query by time
	val start = 10000
	val end = 10500
	val query_time = from db() in "splunkdata" where 
					 SplunkHelper.queryBetweenTimespanDB(start, end) select
					 SplunkHelper.querySplunkDBOToQueryObject("search")

	println(query_time)
	println(query_time.aggregate(
	    SplunkHelper.AggregateByProperty("key"), 
	    SplunkHelper.AggregateSumSplunkData("search")))
	    
	// query by name and time
	val query_time_name = from db() in "splunkdata" where 
					  	  SplunkHelper.queryByUserKeyBetweenTimespanDB("66ad29c2c88ecb081ec7cca125960528", start, end) select
					  	  SplunkHelper.querySplunkDBOToQueryObject("search")

	println(query_time_name)
	println(query_time_name.aggregate(
	    SplunkHelper.AggregateByProperty("key"), 
	    SplunkHelper.AggregateSumSplunkData("search")))
	    
	// top 10 users
	val query_top = (query_time.aggregate(
				     SplunkHelper.AggregateByProperty("key"), 
				     SplunkHelper.AggregateSumSplunkData("search")))
				     .orderbyDecsending(x => x.getProperty[Int]("search")).top(10)

	println(query_top.count)
	println(query_top)
}