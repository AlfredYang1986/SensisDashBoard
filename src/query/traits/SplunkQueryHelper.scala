package query.traits

import query.IQueryable
import query.property.SensisQueryElement

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
	
	/**
	 * @left	: previous query
	 * @right	: current  query
	 * @return  : current  query with trends
	 */
	def splunkQueryCompare(left : IQueryable[SensisQueryElement], right : IQueryable[SensisQueryElement], t : Int) : IQueryable[SensisQueryElement] = {
		def getPercentage(p : Int, c: Int) : String = "%.2f%%".format(100.0 * (c - p) / p)
		def getPositionIncrement(p : Int, c: Int) : String = "%d".format(p - c)
	 
		var index = 0
		for (it <- right) {
			if (index < t) {
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
		right
	}
}
