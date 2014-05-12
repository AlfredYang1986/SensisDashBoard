package login

import query._
import query.helper._
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query.dsl.QueryExpressionObject

object UserLogin {
	def loadInitialAdminstrators(path : String) = {
		val Pete   = MongoDBObject("username" -> "Pete", "password" -> "***")
		val Andrew = MongoDBObject("username" -> "Andrew", "password" -> "***")
		val Karl   = MongoDBObject("username" -> "Karl", "password" -> "***")
		val Willy  = MongoDBObject("username" -> "Willy", "password" -> "***")
		
		if (from db() in "user_login" where Pete )
	}

	def authorization(username : String, password : String) : Boolean = false
	def addAdminstrators(username : String, password : String) = {}
	def deleteAdministrators(username : String) = {}
	def changePassword(username : String, password : String) = {}
}