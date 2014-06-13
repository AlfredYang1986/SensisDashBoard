package resultphrase

import jsondataparse.parser
import query._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query.dsl.QueryExpressionObject
import java.text.SimpleDateFormat

object MasheryReulstHandle extends ResultHandle {
	def apply(result: String) = {
	    def changeTimeFormat(d : String) : String = 
	      	new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(d))
	  
		def getUpdateNewObject(m : Map[String, Any]) : DBObject = 
		    new MongoDBObject(m + ("created" -> changeTimeFormat(m.get("created").get.asInstanceOf[String]), "updated" -> changeTimeFormat(m.get("updated").get.asInstanceOf[String])))
		
		(parser.parse(result, List("result"))
		.get("result").get.asInstanceOf[Map[String, Any]]
		.get("items").get.asInstanceOf[List[Map[String, Any]]]).map { x =>
		  	println(getUpdateNewObject(x).toMap())
//		  	val query = (from db() in "masherydata" where 
//		  			 	 ("username" -> x.get("username").get) select
//		  			 	 (y => y)).fistOrDefault
//		  
//		  	if (query.isEmpty) _data_connection.getCollection("masherydata") += getUpdateNewObject(x)
//		  	else _data_connection.getCollection("masherydata").update(query.get, getUpdateNewObject(x))
		}
	}
}