package login

import query._
import query.helper._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query.dsl.QueryExpressionObject

case class Admin(username : String, password : String)

object UserLogin {
	def loginAsInitialAdmin(username : String, password : String, utype : String) : Boolean = 
		// 1. the origin password is 8888
		// 2. username must be Pete, Andrew, Karl, Willy
		if ("8888" == password && List("Pete", "Andrew", "Karl", "Willy").contains(username)) {
			_data_connection.getCollection("user_name") += MongoDBObject("username" -> username, "password" -> "8888", "utype" -> utype)
			true
		} else false
	
	def authorization(username : String, password : String) : Boolean = 
		if (from db() in "user_name" where ("username" -> username, "password" -> password) contains)  true
		else loginAsInitialAdmin(username, password, "1")

	def addAdmin(username : String, password : String, utype : String) : Boolean = 
		if (from db() in "user_name" where ("username" -> username) contains) false
		else {
		  _data_connection.getCollection("user_name") += MongoDBObject("username" -> username, "password" -> password, "utype" -> utype)
		  true
		}
	
	def deleteAdmin(username : String) : Boolean = 
		(from db() in "user_name" where ("username" -> username) select (x => x)).fistOrDefault match {
		  case Some(e) => _data_connection.getCollection("user_name") -= e; true
		  case none => false
		}
	
	def changePassword(username : String, password : String) : Boolean =
		(from db() in "user_name" where ("username" -> username) select (x => x)).fistOrDefault match {
		  case Some(e) => _data_connection.getCollection("user_name").update(e, e ++ ("password" $eq password)); true
		  case none => false
		}
	
	def level(username : String) = 
		(from db() in "user_name" where ("username" -> username) select (x => x)).fistOrDefault match {
		  case Some(e) => e.get("utype"); e.get("utype")
		  case none => false
		}
	
	
}