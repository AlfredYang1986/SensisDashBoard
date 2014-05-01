package sensis

import java.security._
import java.util.Date

object APIKeyDelegateDispatch {
	def UrlKeyValue(name: String, delegate: String, args: Map[String, String]) : String = {
		def copy(name: String, value: String) : String = name + "=" + value
		def md5Hash(text: String) : String = java.security.MessageDigest.getInstance("MD5").digest(text.getBytes()).map(0xFF & _).map { "%02x".format(_) }.foldLeft(""){_ + _}
	 
		def copyDelegate(ls: List[String]) : String = copy(name, args.get(ls.head).get)
		def md5Delegate : String = 
			  name + "=" + md5Hash(args.get("key").get + 
			  args.get("secret").get + String.valueOf(new Date().getTime() / 1000))
		
		val ls : List[String] = delegate.substring(delegate.indexOf("(") + 1, delegate.indexOf(")")).split(",").toList
		delegate.substring(0, delegate.indexOf("(")) match {
		  case "copy" => copyDelegate(ls)
		  case "md5" => md5Delegate
		  case _ => null
		}
	}
	
}