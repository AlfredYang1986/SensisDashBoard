package query.traits

import scala.util.parsing.json.JSONObject
import cache.SplunkDatabaseName
import query._
import query.property.SensisQueryElement
import com.mongodb.casbah.Imports._
import query.property.QueryElementToJSON

object SplunkQLQuery extends QueryTraits {
	def isQueryable(property : String) : Boolean = false
	def query(b : Int, e : Int, p : SensisQueryElement, r : String*) : JSONObject = ??? // only provide tops because it to slow
	def queryTops(t : Int, b : Int, e : Int, p : SensisQueryElement, r : String*) : JSONObject = {
		QueryElementToJSON(queryAcc(t, b, e, p, Array("times")).orderbyDecsending(x => x.getProperty[Int]("times")).top(t).toList)
	}
	
	private def queryAcc(t : Int, b : Int, e : Int, p : SensisQueryElement, r : Array[String]) : IQueryable[SensisQueryElement] = {
	  	def queryConditions : DBObject = {
	  	  	val builder = MongoDBObject.newBuilder
	  	  	for (it <- p) {
	  	  		builder += it.name -> it.get
	  	  	}
	  	  	
	  	  	builder.result
	  	}
	  	def resultConditons(fl : Array[String]) : MongoDBObject => SensisQueryElement = { x => 
	  	  	var re : SensisQueryElement = new SensisQueryElement
	  	  	re.insertProperty("query", x.getAsOrElse("query", ""))
	  	  	re.insertProperty("location", x.getAsOrElse("location", ""))
	  	  	for (it <- fl) {
	  	  		re.insertProperty(it, x.getAsOrElse(it, 0))
	  	  	}
	  	  	re
	  	}
	  	def unionResult(left : IQueryable[SensisQueryElement], right : IQueryable[SensisQueryElement], fl : Array[String]) : IQueryable[SensisQueryElement] = {
	  		if (left != null) {
	  		  left.union(right)(x => x.getProperty[String]("query") + x.getProperty[String]("location")) { (x, y) => 
	  				if (x == null) y
	  				else if (y == null) x
	  				else {
	  					val re = new SensisQueryElement
	  					re.insertProperty("query", x.getProperty("query"))
	  					re.insertProperty("location", x.getProperty("location"))
	  					for (it <- fl) re.insertProperty(it, x.getProperty[Int](it) + y.getProperty[Int](it))
	  					re
	  				}
	  		   }
	  		}
	  		else right
	  	}
	  	
	  	val fl = r.toArray
	  	var queryCan : IQueryable[SensisQueryElement] = null
	  	for (i <- b to e) {
	  		val cur = SplunkDatabaseName.splunk_query_data.format(i)
	  		val tmp = (from db() in cur where queryConditions).selectTop(t)("times")(resultConditons(fl))
	  		queryCan = unionResult(queryCan, tmp, fl)
	  	}
	  	queryCan
	  	
	  	var query : IQueryable[SensisQueryElement] = null
	  	for (i <- b to e) {
	  		var con : DBObject = null
	  		for (c <- queryCan) {
	  			val tmp_c = $and("query" $eq c.getProperty[String]("query"), "location" $eq c.getProperty[String]("location")) 
	  			if (con == null) con = tmp_c
	  			else con = $or(con, tmp_c)
	  		}
	  		val cur = SplunkDatabaseName.splunk_query_data.format(i)
	  		val tmp = from db() in cur where con select resultConditons(fl)

	  		query = unionResult(query, tmp, fl)
	  	}
	  	query
	}
}
