package query.property

import query.from
import scala.util.parsing.json.JSONArray
import scala.util.parsing.json.JSONObject
import query.QueryOrdering
import java.util.Calendar
import query.BaseTimeSpan
import java.text.SimpleDateFormat

class SensisQueryElement {
	var args : List[Property] = Nil
	
	def getProperty[T](name : String) : T = {
	  	val query = (from[Property] in args where (x => x.name == name) select (x => x)).fistOrDefault
	  	if (query.isEmpty) null.asInstanceOf[T]
	  	else query.get.get.asInstanceOf[T]
	}
	
	def insertProperty[T](name : String, value : T) = {
	  	def propertyInstance : Property = { val n = new CommonProperty[T](name); n.set(value); n }
	  
	  	val query = (from[Property] in args where (x => x.name == name) select (x => x)).fistOrDefault
	  	if (query.isEmpty) args = args :+ propertyInstance
	  	else query.get.asInstanceOf[CommonProperty[T]].set(value)
	}
	
	def contains(name : String) : Boolean = 
		!((from[Property] in args where (x => x.name == name) select (x => x)).fistOrDefault).isEmpty
	
	override def toString = args.toString
	
	def toJSONMap : JSONObject = {
	  	var mp = Map.empty[String, Any]
	  	for (arg <- args) {
	  		if (arg.name != "days") mp += (arg.name -> arg.get)
	  		else {
	  			val cal = Calendar.getInstance()
	  			cal.setTime(BaseTimeSpan.base)
	  			cal.add(Calendar.DATE, arg.get.asInstanceOf[Int])
	  			mp += "days" -> new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())
	  		}
	  	}
	  	new JSONObject(mp)
	}
	
	def foreach[B](f : Property => B) = args foreach f
	def clear = args = Nil

	def split : List[SensisQueryElement] = {
	  	def splitAcc(acc : List[Property]) : List[SensisQueryElement] = {
	  	  	if (acc.isEmpty) Nil
	  	  	else {
	  	  		val tmp = new SensisQueryElement
	  	  		tmp.insertProperty(acc.head.name, acc.head.get)
	  	  		splitAcc(acc.tail) :+ tmp
	  	  	}
	  	}
	  	splitAcc(this.args)
	}
	
	def union(right : SensisQueryElement, filterProperty : String*) : SensisQueryElement = {
	  	if (right == null) this
	  	else {
	  	  for (it <- right) {
	  		  if (!filterProperty.contains(it.name))
	  			  if (this.contains(it.name)) this.insertProperty(it.name, this.getProperty[Int](it.name) + right.getProperty[Int](it.name))
	  			  else this.insertProperty(it.name, right.getProperty[Int](it.name))
	  	  }
	  	  this 
	  	}
	}
	
	def orderValue : SensisQueryElement = {
		val re = new SensisQueryElement
		re.args = args.sortBy(x => x.get.asInstanceOf[Int])(new QueryOrdering[Int])
		re
	}
	def orderDecendingValue : SensisQueryElement = {
		val re = new SensisQueryElement
		re.args = args.sortBy(x => x.get.asInstanceOf[Int])(new QueryOrdering[Int]).reverse
		re
	}
	def removeProperty(name : String) : Unit = args = args.filter(x => x.name != name)
	def removeProperty(p : Property) : Unit = removeProperty(p.name)
}
