package unit_test

import query.from
import query.QueryHelper

object AlfredSearchDemoForTony extends App {
	// query by key 
	val query = from db() in "splunkdata" where 
				QueryHelper.queryByUserKeyDB("66ad29c2c88ecb081ec7cca125960528") select
				QueryHelper.querySplunkDBOToQueryObject("search")
	
	println(query)
	println(query.aggregate(
	    QueryHelper.AggregateByProperty("key"), 
	    QueryHelper.AggregateSumSplunkData("search")))
	    
	// query by time
	val start = 10000
	val end = 10500
	val query_time = from db() in "splunkdata" where 
					 QueryHelper.queryBetweenTimespanDB(start, end) select
					 QueryHelper.querySplunkDBOToQueryObject("search")

	println(query_time)
	println(query_time.aggregate(
	    QueryHelper.AggregateByProperty("key"), 
	    QueryHelper.AggregateSumSplunkData("search")))
	    
	// query by name and time
	val query_time_name = from db() in "splunkdata" where 
					  	  QueryHelper.queryByUserKeyBetweenTimespanDB("66ad29c2c88ecb081ec7cca125960528", start, end) select
					  	  QueryHelper.querySplunkDBOToQueryObject("search")

	println(query_time_name)
	println(query_time_name.aggregate(
	    QueryHelper.AggregateByProperty("key"), 
	    QueryHelper.AggregateSumSplunkData("search")))
	    
	// top 10 users
	val query_top = (query_time.aggregate(
				     QueryHelper.AggregateByProperty("key"), 
				     QueryHelper.AggregateSumSplunkData("search")))
				     .orderbyDecsending(x => x.getProperty[Int]("search")).top(10)

	println(query_top.count)
	println(query_top)
}