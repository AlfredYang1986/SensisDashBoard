package query.traits

import scala.util.parsing.json.JSONObject
import query.IQueryable
import query.property.SensisQueryElement
import com.mongodb.casbah.Imports._
import cache.SplunkDatabaseName
import query.from
import query.property.QueryElementToJSON

object SplunkRequestQuery extends QueryTraits {
	def isQueryable(property : String) : Boolean = false
	
	def query(b : Int, e : Int, p : SensisQueryElement, r : String*) : JSONObject = {
	  	QueryElementToJSON(query_acc(b, e, p, r.toArray).toList)
	}
	def queryWithQueryable(b : Int, e : Int, p : SensisQueryElement, r : String*) : IQueryable[SensisQueryElement] = {
		query_acc(b, e, p, r.toArray)
	}
	def queryTops(t : Int, b : Int, e : Int, p : SensisQueryElement, r : String*) : JSONObject = ???
	def queryTopsWithQueryable(t : Int, b : Int, e : Int, p : SensisQueryElement, r : String*) : IQueryable[SensisQueryElement] = ???
	
<<<<<<< HEAD
	def query_acc(b : Int, e : Int, p : SensisQueryElement, r : String*) : IQueryable[SensisQueryElement] = {
=======
	private def query_acc(b : Int, e : Int, p : SensisQueryElement, r : Array[String]) : IQueryable[SensisQueryElement] = {
>>>>>>> FETCH_HEAD
		
		def getFirstElement : String = p.getProperty[String]("first")
		def getSecendElement : String = p.getProperty[String]("secend")

		val first = getFirstElement
		val secend = getSecendElement
		
		def getSearchInsightDBObject : MongoDBObject = {
			val builder = MongoDBObject.newBuilder
			builder += ("first" -> first)
			builder += ("secend" -> secend)
			
			builder.result
		}
		def resultConditons : MongoDBObject => SensisQueryElement = { x => 
	  	  	var re : SensisQueryElement = new SensisQueryElement
	  	  	re.insertProperty("first", x.getAsOrElse("first", ""))
	  	  	re.insertProperty("secend", x.getAsOrElse("secend", ""))
	  	  	re.insertProperty("times", x.getAsOrElse("times", 0))
	  	  	re
	  	}
		def getOneDayData(d : Int) : IQueryable[SensisQueryElement] = {
	  		val cur = SplunkDatabaseName.splunk_request_data.format(d)
	  		from db() in cur where getSearchInsightDBObject select resultConditons
		}
	  	def unionResult(left : IQueryable[SensisQueryElement], right : IQueryable[SensisQueryElement]) : IQueryable[SensisQueryElement] = {
			if (left != null) left.union(right)(x => x.getProperty[String]("first") + x.getProperty[String]("secend")) { (x, y) => 
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
	  	}
	  	
		if (first == null && secend == null) null
		else {
			var query : IQueryable[SensisQueryElement] = null
			for (i <- b to e) query = unionResult(query, getOneDayData(i))
			query
		}
	}
}