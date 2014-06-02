package sqtable

import query._
import query.helper._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query.dsl.QueryExpressionObject

case class table(days: Int, Yellow: String, SAPI: String, SAPI_Comment: String, Yellow_Comment: String, One_Search: String, One_Search_Comment: String) 

  object SQtable {

    def deleterow(days: Int): Boolean =
      (from db () in "search_quality_data" where ("days" -> days) select (x => x)).fistOrDefault match {
        case Some(e) =>
          _data_connection.getCollection("search_quality_data") -= e; true
        case none => false
      }

  }