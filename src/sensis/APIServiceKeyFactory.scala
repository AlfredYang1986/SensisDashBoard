package sensis

abstract class APIKeyBase {
	var args : Map[String, String] = Map.empty
	var UrlString : String = ""
	def AddKeyArg(name: String, value: String) = args += (name -> value)
	def AppendUrlString(name: String, delegate: String, args : Map[String, String])
}

object BaseKey extends APIKeyBase {
	def AppendUrlString(name: String, delegate: String, args : Map[String, String]) = {
		UrlString += APIKeyDelegateDispatch.UrlKeyValue(name, delegate, args)
	}
}

object APIServiceKeyDispatchFactory {
	def APIKeyDispatch(name: String) : APIKeyBase = {
		name match {
		  case "BaseKey" => BaseKey
//		  case "JOSN-RPC" => JSONRPCKey
		  case _ => { 
		    println("Error, generating Key") 
		    null 
		  }
		}
	}
}