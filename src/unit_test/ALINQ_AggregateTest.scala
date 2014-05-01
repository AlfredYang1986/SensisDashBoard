package unit_test

import query.from

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
}