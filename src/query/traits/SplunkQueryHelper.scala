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
}