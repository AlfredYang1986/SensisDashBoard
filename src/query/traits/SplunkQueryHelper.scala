package query.traits

import query.IQueryable
import query.property.SensisQueryElement
import query.Linq_List

object SplunkQueryHelper {
	def unionResultBaseOnUser(left : IQueryable[SensisQueryElement], right : IQueryable[SensisQueryElement], fl : Array[String]) : IQueryable[SensisQueryElement] = {
		if (left != null) left.union(right)(x => x.getProperty[String]("key")) { (x, y) => 
	  		if (x == null) y
	  		else if (y == null) x
	  		else {
	  			val re = new SensisQueryElement
	  			re.insertProperty("key", x.getProperty[String]("key"))
	  			for (it <- fl) re.insertProperty(it, x.getProperty[Int](it) + y.getProperty[Int](it))
	  			re
	  			}
	  		}
	  		else right
	  	}
	
	def unionResultBaseOnEndPoint(left : IQueryable[SensisQueryElement], right : IQueryable[SensisQueryElement], fl : Array[String]) : IQueryable[SensisQueryElement] = {
		if (left != null) left.unionAll(right) { (x, y) => 
	  		if (x == null) y
	  		else if (y == null) x
	  		else {
	  			val re = new SensisQueryElement
	  			for (it <- fl) re.insertProperty(it, x.getProperty[Int](it) + y.getProperty[Int](it))
	  			re
	  			}
	  		}
	  		else right
	  	}
	
	def unionRequestResult[T](prep : SensisQueryElement => T)(left : IQueryable[SensisQueryElement], right : IQueryable[SensisQueryElement])  : IQueryable[SensisQueryElement] = 
	  	if (left != null) left.union(right)(prep) { (x, y) => 
	  		if (x == null) y
	  		else if (y == null) x
	  		else {
	  			val re = new SensisQueryElement
	  			re.insertProperty("first", x.getProperty[String]("first"))
	  			re.insertProperty("secend", x.getProperty[String]("secend"))
	  			re.insertProperty("times", x.getProperty[Int]("times") + y.getProperty[Int]("times"))
	  			re
	  		}
	  	}
	  	else right
	
	def unionRequestQueryCounts(query : IQueryable[SensisQueryElement]) : IQueryable[SensisQueryElement] = {
	  	val reVal = new Linq_List[SensisQueryElement]
	  	var reColl : List[SensisQueryElement] = Nil
	  	var times : Int = 0
	  	for (it <- query) {
	  		val tmp = it.getProperty[String]("secend")
	  		if (tmp.toInt < 10) reColl = reColl :+ it 
	  		else times = times + it.getProperty[Int]("times")
	  	}
	  	
	  	val other = new SensisQueryElement
	  	other.insertProperty("first", query.toList.head.getProperty[String]("first"))
	  	other.insertProperty("secend", "others")
	  	other.insertProperty("times", times)
	  	
	  	reColl = reColl :+ other
	  	reVal.coll = reColl
	  	reVal
	}
	
	def count2String(c : String, p : SensisQueryElement) : String = 
	  if (p.contains("line") && p.getProperty[String]("first") == "queryCount") c match {
	  	case "1" => "one"
	  	case "2" => "two"
	  	case "3" => "three"
	  	case "4" => "four"
	  	case "5" => "five"
	  	case "6" => "six"
	  	case "7" => "seven"
	  	case "8" => "eight"
	  	case "9" => "nine"
	  	case _ => "others" 
	  } else c
	  	
	/**
	 * @left	: previous query
	 * @right	: current  query
	 * @return  : current  query with trends
	 */
	def splunkQueryCompare(left : IQueryable[SensisQueryElement], right : IQueryable[SensisQueryElement], t : Int) : IQueryable[SensisQueryElement] = {
		def getPercentage(p : Int, c: Int) : String = "%.2f%%".format(100.0 * (c - p) / p)
		def getPercentageForOrdering(p : Int, c: Int) : Double = 100.0 * (c - p) / p
		def getPositionIncrement(p : Int, c: Int) : Int = p - c
//		def getPositionIncrement(p : Int, c: Int) : String = "%d".format(p - c)
	
		if (right == null) null
		
		var index = 0
		for (it <- right) {
			if (index < t) {
				if (left == null) {
					it.insertProperty("trends", "new")
					it.insertProperty("pos", "new")
				} else {
					val (pos, ins) = left.contains(it)( (x, y) => x.getProperty[String]("query") == y.getProperty[String]("query") && x.getProperty[String]("location") == y.getProperty[String]("location"))
					if (ins != null) {
						it.insertProperty("trends", getPercentage(ins.getProperty[Int]("times"), it.getProperty[Int]("times")))
						it.insertProperty("pos", getPositionIncrement(pos, index))
					} else {
						it.insertProperty("trends", "new")
						it.insertProperty("pos", "new")
					}
					index = index + 1
				}
			}
		}
		right
	}
	def splunkQueryHotQuery(q : IQueryable[SensisQueryElement]) : IQueryable[SensisQueryElement] = {
		try {
			q.filter(x => !(x.getProperty("pos").isInstanceOf[String])).orderbyDecsending(x => x.getProperty[Int]("pos"))
		  
		} catch {
		  case _ => q
		}
	}
	def splunkQueryColdQuery(q : IQueryable[SensisQueryElement]) : IQueryable[SensisQueryElement] = {
		try {
			q.filter(x => !(x.getProperty("pos").isInstanceOf[String])).orderby(x => x.getProperty[Int]("pos"))
		  
		} catch {
		  case _ => q
		}
	}
}
