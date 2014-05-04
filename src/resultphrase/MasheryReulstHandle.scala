package resultphrase

import jsondataparse.parser
import query._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query.dsl.QueryExpressionObject

object MasheryReulstHandle extends ResultHandle {
	def apply(result: String) = {
		def getUpdateNewObject(m : Map[String, Any]) : DBObject = new MongoDBObject(m)
	  
		(parser.parse(result, List("result"))
		.get("result").get.asInstanceOf[Map[String, Any]]
		.get("items").get.asInstanceOf[List[Map[String, Any]]]).map { x =>
		  	println(x)
		  	val query = (from db() in "masherydata" where 
		  			 	 ("username" -> x.get("username").get) select
		  			 	 (y => y)).fistOrDefault
		  
		  	if (query.isEmpty) _data_connection.getCollection("masherydata") += getUpdateNewObject(x)
		  	else _data_connection.getCollection("masherydata").update(query.get, getUpdateNewObject(x))
		}
	}
}