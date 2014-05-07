package unit_test

import query._
import query.helper._

case class ag_test(user : String, value : Int)

object ALINQ_AggregateTest extends App {
	var ls: List[ag_test] = Nil 
	ls = ls :+ new ag_test("yang", 1)
	ls = ls :+ new ag_test("yuan", 2)
	ls = ls :+ new ag_test("yang", 3)
	ls = ls :+ new ag_test("yuan", 4)
	ls = ls :+ new ag_test("yang", 5)
	ls = ls :+ new ag_test("yuan", 6)
	
	val query = from[ag_test] in 
				ls select (x=>x)
	
	println(query)
	println(query.orderby(x => x.user))
	println(query.orderby(x => x.user).top(2))

	for (it <- query) println(it)
	
	println(query.distinctBy(x => x.user))
	
	val agg = query.aggregate(x => x.user
	    , ls => {
	    		var sum = 0
	    		for (it <- ls) sum = sum + it.value
	    		new ag_test(ls.head.user, sum)
	    })
	    
	println(agg)

	val t = null.asInstanceOf[Int]
	t match {
	  case tm : Int => println(tm)
	  case _ => println("Other")
	}
	
	val query_db = from db() in "splunkdata" where 
				   (SplunkHelper.queryByUserKeyDB("71da34f94aaae4d4d4d7b9d930b275d2")) select 
				   SplunkHelper.querySplunkDBOToQueryObject("search", "singleSearch")
				   
	println(query_db)
	
	println(query_db.distinctBy(SplunkHelper.discintByUserKey))
	println(query_db.aggregate(SplunkHelper.discintByUserKey, SplunkHelper.AggregateSumSplunkData("search")))
	println(query_db.aggregate(SplunkHelper.AggregateByProperty("key"), SplunkHelper.AggregateSumSplunkData("search", "singleSearch")))
}






