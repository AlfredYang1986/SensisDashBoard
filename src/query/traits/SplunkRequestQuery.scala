package query.traits

import scala.util.parsing.json.JSONObject
import query.IQueryable
import query.property.SensisQueryElement
import com.mongodb.casbah.Imports._
import cache.SplunkDatabaseName
import query.from
import query.property.QueryElementToJSON
import scala.util.parsing.json.JSONArray

object SplunkRequestQuery extends QueryTraits {
	def isQueryable(property : String) : Boolean = false
	
	def query(b : Int, e : Int, p : SensisQueryElement, r : String*) : JSONObject = {
		if (p.contains("pie")) 
			QueryElementToJSON(query_acc(b, e, p, r.toArray)
			    (SplunkQueryHelper.unionRequestResult(x => x.getProperty[String]("first") + x.getProperty[String]("secend"))).toList)
		else if (p.contains("line")) {
			val tmp = query_acc_with_queryable(b, e, p, r.toArray)

			var re : Map[String, JSONArray] = Map.empty
			val dis = tmp.distinctBy(x => x.getProperty[String]("secend")).toList.distinct
			for (sec <- dis) {
				var reVal : List[JSONObject] = Nil
				val xs = from[SensisQueryElement] in tmp.toList where (x => x.getProperty[String]("secend") == sec) select (x =>x)
				for (it <- xs) reVal = reVal :+ (it.toJSONMap)
				
				re += sec -> new JSONArray(reVal)
			}
			
			new JSONObject(re)
		}
		else ???
	}
	def queryWithQueryable(b : Int, e : Int, p : SensisQueryElement, r : String*) : IQueryable[SensisQueryElement] = query_acc_with_queryable(b, e, p, r.toArray)

	def queryTops(t : Int, b : Int, e : Int, p : SensisQueryElement, r : String*) : JSONObject = ???
	def queryTopsWithQueryable(t : Int, b : Int, e : Int, p : SensisQueryElement, r : String*) : IQueryable[SensisQueryElement] = ???
	
	private def query_acc_with_queryable(b : Int, e : Int, p : SensisQueryElement, r : Array[String]) : IQueryable[SensisQueryElement] = {
		if (p.contains("pie")) 
			query_acc(b, e, p, r.toArray)(SplunkQueryHelper.unionRequestResult(x => x.getProperty[String]("first") + x.getProperty[String]("secend")))
		else if (p.contains("line")) 
			query_acc(b, e, p, r.toArray)(SplunkQueryHelper.unionRequestResult(x => x.getProperty[Int]("days") + x.getProperty[String]("first") + x.getProperty[String]("secend")))
		else ???
	}
	private def query_acc(b : Int, e : Int, p : SensisQueryElement, r : Array[String])
						 (f : (IQueryable[SensisQueryElement], IQueryable[SensisQueryElement]) => IQueryable[SensisQueryElement]) 
						 : IQueryable[SensisQueryElement] = {
		
		def getFirstElement : String = p.getProperty[String]("first")
		def getSecendElement : String = p.getProperty[String]("secend")

		val first = getFirstElement
		val secend = getSecendElement
		
		def getSearchInsightDBObject : DBObject = {
			val builder = MongoDBObject.newBuilder
			
			if (first != null) builder += ("first" -> first)
			if (secend != null) builder += ("secend" -> secend)
			
			builder.result
		}
		def resultConditons(days : Int) : MongoDBObject => SensisQueryElement = { x => 
	  	  	var re : SensisQueryElement = new SensisQueryElement
	  	  	if (p.contains("line")) re.insertProperty("days", days)
	  	  	
	  	  	re.insertProperty("first", x.getAsOrElse("first", ""))
	  	  	re.insertProperty("secend", x.getAsOrElse("secend", ""))
	  	  	re.insertProperty("times", x.getAsOrElse("times", 0))
	  	  	re
	  	}
		def getOneDayData(d : Int) : IQueryable[SensisQueryElement] = {
	  		val cur = SplunkDatabaseName.splunk_request_data.format(d)
	  		from db() in cur where getSearchInsightDBObject select resultConditons(d)
		}
	  	def unionResult(left : IQueryable[SensisQueryElement], right : IQueryable[SensisQueryElement]) : IQueryable[SensisQueryElement] = f(left, right)
	  	
		if (first == null && secend == null) null
		else {
			var query : IQueryable[SensisQueryElement] = null
			for (i <- b to e) query = unionResult(query, getOneDayData(i))
			query
		}
	}
}
