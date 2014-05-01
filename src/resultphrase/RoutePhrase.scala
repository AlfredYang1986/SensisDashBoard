package resultphrase

import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.DBObject

object RoutePhraseSplunk {
  var re: Map[String, String] = Map.empty
 
  def getUserKey : String = re.get("key").get
  def getFunctionCalls : Map[String, Any] = {
	  var iter = re.iterator
	  re -= "key"
	  var reVal : Map[String, Int] = Map.empty
	  while (iter.hasNext) {
		  val (key, _) = iter.next
		  reVal += (key -> 1)
	  }
	  reVal
  }
    
  def addFunctionCalls(left : MongoDBObject, right: String): DBObject = {
		val bd = MongoDBObject.newBuilder
		val method_name = right.replace('.', '_')
		var times = left.getAsOrElse(method_name, 0)
		bd += (method_name -> (times + 1))
		(left ++ bd.result)
    }
  
  def phraseMethodName(s: String): String = {
    if (s.contains('?')) s.substring(s.indexOf('/') + 1, s.indexOf('?'))
    else s.substring(s.indexOf('/') + 1, s.lastIndexOf(' '))
  }
  
//  def phraseArguments(s: String): List[String] = {
  def phraseArguments(s: String): Map[String, String] = {
//    var re: Map[String, String] = Map.empty
//    var re: List[String] = Nil
    re = Map.empty
    if (!s.contains('?')) return re

    val sq = s.substring(s.indexOf('?') + 1, s.lastIndexOf(' '))
    val sl: List[String] = sq.split("&").toList
    for (it <- sl) {
      if (!it.isEmpty() && it.contains('=')) {
//        val s_1 = it.substring(0, it.indexOf('='))
//        val s_2 = it.substring(it.indexOf('=') + 1)
        re += (it.substring(0, it.indexOf('=')) -> it.substring(it.indexOf('=') + 1))
//          re :+ it.substring(0, it.indexOf('='))
      }
    }
    re
  }
}