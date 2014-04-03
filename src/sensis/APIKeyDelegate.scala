package sensis

import java.security._

object APIKeyDelegateDispatch {
	def UrlKeyValue(name: String, delegate: String, args: Map[String, String]) : String = {
		val func : String = delegate.substring(0, delegate.indexOf("("))
		val ls : List[String] = delegate.substring(delegate.indexOf("(") + 1, delegate.indexOf(")")).split(",").toList
		func match {
		  case "copy" => {
			  val a = args.get(ls.head).get
			  copy(name, a)
		  }
		  case "md5" => { 
			  var key : String = ""
			  var secret : String = ""
			  var time : String = ""
			  for (a <- ls) {
				  a match {
				    case "key" => key = args.get("key").get
				    case "secret" => secret = args.get("secret").get
				    case "time" => time = new java.sql.Timestamp(System.currentTimeMillis()).toGMTString()
				    case _ => {
				      println("error argments for md5")
				      null
				    }
				  }
			  }
			  name + "=" + md5Hash(key + secret + time)
		  }
		  case _ => null
		}
	}
	
	private def copy(name: String, value: String) : String = name + "=" + value
	private def md5Hash(text: String) : String = java.security.MessageDigest.getInstance("MD5").digest(text.getBytes()).map(0xFF & _).map { "%02x".format(_) }.foldLeft(""){_ + _}
}