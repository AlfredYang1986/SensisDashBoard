package query

import query.property.SensisQueryElement
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query.dsl.QueryExpressionObject

object QueryHelper {
	def queryByUserKey(key: String) : SensisQueryElement => Boolean = x => key == x.getProperty("key")
	
	def queryByUserKeyDB(key: String) : DBObject = ("key" $eq key)

	def queryBetweenTimespan(st: Int, ed: Int) : SensisQueryElement => Boolean = x => 
	  	x.getProperty[Int]("days") >= st && x.getProperty[Int]("days") <= ed

	def queryBetweenTimespanDB(st: Int, ed: Int) : DBObject = ("days" $gte st $lte ed)
	
	def queryByUserKeyBetweenTimespan(key: String, st: Int, ed: Int) : SensisQueryElement => Boolean = 
		x => (key == x.getProperty("key")) && x.getProperty[Int]("days") >= st && x.getProperty[Int]("days") <= ed
	
	def queryByUserKeyBetweenTimespanDB(key: String, st: Int, ed: Int) : DBObject = ("key" $eq key) ++ ("days" $gte st $lte ed)
	
	def discintByUserKey[T] : T => String = x => x match {
	  case x : SensisQueryElement => x.getProperty[String]("key")
	  case _ => ???
	}

	def AggregateByProperty[A, T](name: String) : A => T = x => x match {
	  case x : SensisQueryElement => x.getProperty(name)
	  case _ => ???
	}

//	def AggregateSumBySearch : List[SensisQueryElement] => SensisQueryElement = ls => {
	def AggregateSumSplunkData(args : String*) : List[SensisQueryElement] => SensisQueryElement = ls => {
		val reVal = new SensisQueryElement
		reVal.insertProperty("key", ls.head.getProperty("key"))
		for (it <- ls) //search = search + it.getProperty[Int]("search") 
			for (arg <- args)
				reVal.insertProperty(arg, reVal.getProperty[Int](arg) + it.getProperty[Int](arg))
		reVal
	}
	
	def querySplunkDBOToQueryObject(args : String*) : MongoDBObject => SensisQueryElement = x => {
		val reVal = new SensisQueryElement
		reVal.insertProperty("key", x.getAs[String]("key").get)
		for (it <- args) {
			if (it != "key" && it != "days")
		    reVal.insertProperty(it, x.getAsOrElse(it, 0))
		}
		reVal
	}
}