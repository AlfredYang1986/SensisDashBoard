package unit_test

import query._
import query.helper._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query.dsl.QueryExpressionObject
import query.property.QueryElementToJSON

object AlfredMasheryDataForTony extends App {
//	val query = from db() in "masherydata" select
//				MasheryHelper.queryMasheryDBOToQueryObject("first_name", "email")
//	
//	println(query)
//	query.toList.map(x => x.args.map{ y => 
//	  	println(y)
//	})
//	
//	println(QueryElementToJSON(query.toList))
  
	val query = from db() in "10298_splunk_data" select (x => x)
	println(query)
	println
	val query1 = from db() in "10298_splunk_query" select (x => x)
	println(query1.top(100))
}
