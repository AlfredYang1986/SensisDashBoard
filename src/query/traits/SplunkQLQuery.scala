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
		val (key, func) = getQueryKey(p)
	    QueryElementToJSON(queryAcc(t, b, e, p, Array("times"))(func).orderbyDecsending(x => x.getProperty[Int]("times")).top(t).toList)
	}
	def queryWithQueryable(b : Int, e : Int, p : SensisQueryElement, r : String*) : IQueryable[SensisQueryElement] = {
		val (key, func) = getQueryKey(p)
	    queryAcc(-1, b, e, p, Array("times"))(func).orderbyDecsending(x => x.getProperty[Int]("times"))
	}
	def queryTopsWithQueryable(t : Int, b : Int, e : Int, p : SensisQueryElement, r : String*) : IQueryable[SensisQueryElement] = {
		val (key, func) = getQueryKey(p)
	    queryAcc(t, b, e, p, Array("times"))(func).orderbyDecsending(x => x.getProperty[Int]("times")).top(t)
	}

	private def getQueryKey(p : SensisQueryElement) : (String, (IQueryable[SensisQueryElement], IQueryable[SensisQueryElement], Array[String]) => IQueryable[SensisQueryElement]) = {
		if (p.contains("query_only")) ("query", SplunkQueryHelper.queryUnionResult)
		else if (p.contains("location_only")) ("location", SplunkQueryHelper.locationUnionResult)
		else ("QL", SplunkQueryHelper.unionResult)
	}
	
	private def queryAcc(t : Int, b : Int, e : Int, p : SensisQueryElement, r : Array[String])
				(unionResult : (IQueryable[SensisQueryElement], IQueryable[SensisQueryElement], Array[String]) => IQueryable[SensisQueryElement]) 
				: IQueryable[SensisQueryElement] = {
	  	def queryConditions : DBObject = {
	  	  	val builder = MongoDBObject.newBuilder
	  	  	for (it <- p) {
	  	  		if (it.name != "query_only" && it.name != "location_only")
	  	  			builder += it.name -> it.get
	  	  	}
	  	  	
	  	  	builder.result
	  	}
	  	
	  	def resultConditons(fl : Array[String]) : MongoDBObject => SensisQueryElement = { x => 
	  		var re : SensisQueryElement = new SensisQueryElement
	  		if (p.contains("query_only")) re.insertProperty("query", x.getAsOrElse("query", ""))
	  		else if (p.contains("location_only")) re.insertProperty("location", x.getAsOrElse("location", ""))
	  		else {
	  			re.insertProperty("query", x.getAsOrElse("query", ""))
	  			re.insertProperty("location", x.getAsOrElse("location", ""))
	  		}
	  		for (it <- fl) {
	  			re.insertProperty(it, x.getAsOrElse(it, 0))
	  		}
	  		re
	  	}

	  	val fl = r.toArray
	  	def getQuery(d : String) : IQueryable[SensisQueryElement] = 
	  		if (t > 0) { val re = (from db() in d where queryConditions).selectTop(t)("times")(resultConditons(fl)); println(re); re }
	  		else from db() in d where queryConditions select resultConditons(fl)
	  	
	  	var queryCan : IQueryable[SensisQueryElement] = null
	  	for (i <- b to e) {
	  		val cur = if (p.contains("query_only")) SplunkDatabaseName.splunk_query_only_data.format(i)
	  				  else if (p.contains("location_only")) SplunkDatabaseName.splunk_location_only_data.format(i) 
	  				  else SplunkDatabaseName.splunk_query_data.format(i)

	  		val tmp = getQuery(cur)
	  		queryCan = unionResult(queryCan, tmp, fl)
	  	}
	  	
	  	var query : IQueryable[SensisQueryElement] = null
	  	for (i <- b to e) {
	  		var con : DBObject = null
	  		for (c <- queryCan) {
	  			val tmp_c = $and("query" $eq c.getProperty[String]("query"), "location" $eq c.getProperty[String]("location")) 
	  			if (con == null) con = tmp_c
	  			else con = $or(con, tmp_c)
	  		}
	  		val cur = if (p.contains("query_only")) SplunkDatabaseName.splunk_query_only_data.format(i)
	  				  else if (p.contains("location_only")) SplunkDatabaseName.splunk_location_only_data.format(i) 
	  				  else SplunkDatabaseName.splunk_query_data.format(i)

	  		val tmp = from db() in cur where con select resultConditons(fl)

            query = unionResult(query, tmp, fl)
        }
        query
    }

    def getQueryAsList(t: Int, b: Int, e: Int, p: SensisQueryElement, r: Array[String]): List[SensisQueryElement] = {
		val (key, func) = getQueryKey(p)
	    queryAcc(t, b, (b + 7), p, Array("times"))(func).top(t).toList
    }
}
