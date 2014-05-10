package sensis

import com.splunk.ServiceArgs

import errorreport._

abstract class APIKeyBase {
	var args : Map[String, String] = Map.empty
	var UrlString : String = ""
	def AddKeyArg(name: String, value: String) = args += (name -> value)
	def AppendUrlString(name: String, delegate: String, args : Map[String, String])
}

object BaseKey extends APIKeyBase {
	def AppendUrlString(name: String, delegate: String, args : Map[String, String]) = {
		if (!UrlString.isEmpty()) UrlString += "&"
		UrlString += APIKeyDelegateDispatch.UrlKeyValue(name, delegate, args)
	}
}

object JSONRPCKey extends APIKeyBase {
	def AppendUrlString(name: String, delegate: String, args : Map[String, String]) = {
		if (!UrlString.isEmpty()) UrlString += "&"
		UrlString += APIKeyDelegateDispatch.UrlKeyValue(name, delegate, args)
	}
}

object SplunkKey extends APIKeyBase {
	var loginArgs : ServiceArgs = new ServiceArgs()
	override def AddKeyArg(name: String, value: String) = {
		name match {
		  case "username" => loginArgs.setUsername(value)
		  case "password" => loginArgs.setPassword(value)
		  case "host" 	 => loginArgs.setHost(value)
		  case "port" 	 => loginArgs.setPort(value.toInt)
		  case _ => throw Error_PhraseXML
		}
	}
	def AppendUrlString(name: String, delegate: String, args : Map[String, String]) = {}
}

object APIServiceKeyDispatchFactory {
	def APIKeyDispatch(name: String) : APIKeyBase = {
		name match {
		  case "BaseKey" => BaseKey
		  case "MasheryKey" => JSONRPCKey
		  case "SplunkKey" => SplunkKey
		  case _ => { 
		    println("Error, generating Key") 
		    null 
		  }
		}
	}
}