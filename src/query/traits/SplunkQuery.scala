package query.traits

import scala.util.parsing.json.JSONObject
import cache.SplunkDatabaseName
import query._
import query.property.SensisQueryElement
import com.mongodb.casbah.Imports._
import query.property.QueryElementToJSON

object SplunkQuery extends QueryTraits {
	def support: List[String] = List("overall", "yello", "non-yello")
  
	def isQueryable(property : String) : Boolean = support.contains(property)
	def query(b : Int, e : Int, p : SensisQueryElement, r : String*) : JSONObject = {
	  	QueryElementToJSON(queryAcc(b, e, p, r.toArray).toList)
	}
	def queryTops(t : Int, b : Int, e : Int, p : SensisQueryElement, r : String*) : JSONObject = {
	  	val re = queryAcc(b, e, p, r.toArray).orderbyDecsending(x => x.getProperty(r.head)).top(t)
	  	QueryElementToJSON(re.toList)
	}
	
	private def queryAcc(b : Int, e : Int, p : SensisQueryElement, r : Array[String]) : IQueryable[SensisQueryElement] = {
	  	def queryConditions : DBObject = {
	  	  	val builder = MongoDBObject.newBuilder
	  	  	for (it <- p) {
	  	  		builder += it.name -> it.get
	  	  	}
	  	  	
	  	  	builder.result
	  	}
	  	def resultConditons(fl : Array[String]) : MongoDBObject => SensisQueryElement = { x => 
	  	  	var re : SensisQueryElement = new SensisQueryElement
	  	  	re.insertProperty("key", x.getAsOrElse("key", ""))
	  	  	for (it <- fl) {
	  	  		re.insertProperty(it, x.getAsOrElse(it, 0))
	  	  	}
	  	  	re
	  	}
	  	def unionResult(left : IQueryable[SensisQueryElement], right : IQueryable[SensisQueryElement], fl : Array[String]) : IQueryable[SensisQueryElement] = {
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
	  	
	  	var fl : Array[String] = null
	  	if (r.length == 1 && r.head == "*") {
	  		val l = from db() in "splunk_end_points" select (x => x.getAsOrElse("function_name", ""))
	  		val l2 = from[String] in l.toList where (x => !x.contains("/")) select (x => x)
	  		fl = l2.toList.toArray
	  	} else fl = r.toArray
	  
	  	def getOneDayResult(di : Int) : IQueryable[SensisQueryElement] = {
	  		val cur = SplunkDatabaseName.splunk_raw_data.format(di)
	  		if (p.contains("yello")) from db() in cur where queryConditions select resultConditons(fl)
	  		else {
	  			val tmp1 = from db() in cur where queryConditions ++ ("yello" -> true) select resultConditons(fl)
	  			val tmp2 = from db() in cur where queryConditions ++ ("yello" -> false) select resultConditons(fl)
	  			unionResult(tmp1, tmp2, fl)
	  		}
	  	}
	  	
	  	var query : IQueryable[SensisQueryElement] = null
	  	for (i <- b to e) query = unionResult(query, getOneDayResult(i), fl)
	  	query
	}
}
