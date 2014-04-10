/**
 * One web server is using JOSN-RPC
 * building a strategy to dynamic generate API query arguments
 * Created By Alfred Yang
 * 2nd April, 2014
 */

package sensis

import java.net.URLEncoder._
import scala.xml.Node
import scala.util.parsing.json._

abstract class APIArgumentsBase {
	def AddArg(name: String, value: String, strType: String)
	def toString : String
}

abstract class APIArgumentFactory {
	def ArgsInstance(elem: scala.xml.Node) : APIArgumentsBase
}

object APIRouteArgumentsFactory extends APIArgumentFactory {
	def ArgsInstance(elem: scala.xml.Node) = {
		var result = new APIWebServiceRouteArguments 
		val args = (elem \ "arg").map { ag =>
			result.AddArg((ag \ "@name"). text, (ag \ "@value").text, (ag \ "@type").text)
		}
		result
	}
}

class APIWebServiceRouteArguments extends APIArgumentsBase {
  	case class APIRouteArg(name: String, originValue: String) {
  		override def toString = "&" + name + "=%s".format(encode(originValue, "UTF-8"))
  	}
  	
  	private var args : List[APIRouteArg] = Nil
	override def AddArg(name: String, value: String, strType: String) = {
  		args = new APIRouteArg(name, value)::args
  	}
  	
  	override def toString = {
  		var r = ""
  		for (arg <- args) r += arg.toString
  		r
  	}
}

object APIJOSNRPCArgumentsFactory extends APIArgumentFactory {
	def ArgsInstance(elem: scala.xml.Node) = {
		var result = new APIJOSNRPCArguments
		val args = (elem \ "arg").map { ag =>
			result.AddArg((ag \ "@name"). text, (ag \ "@value").text, (ag \ "@type").text)
		}
		result
	}
}

class APIJOSNRPCArguments extends APIArgumentsBase {
	var args : Map[String, Any] = Map.empty
  
	override def AddArg(name: String, value: String, strType: String) = {
  		args += (name -> APIArgsTypeDelegate.ValueInstance(value, ',', strType))
  	}
  	
  	override def toString = {
  	  	new JSONObject(args).toString
  	}
}

object APISplunkArgumnetsFactory extends APIArgumentFactory {
	def ArgsInstance(elem: scala.xml.Node) = {
		var result = new APIJOSNRPCArguments
		val args = (elem \ "arg").map { ag =>
			result.AddArg((ag \ "@name"). text, (ag \ "@value").text, (ag \ "@type").text)
		}
		result
	}
}

class APISplunkArguments extends APIArgumentsBase {
	var args : Map[String, Any] = Map.empty
	override def AddArg(name: String, value: String, strType: String) = {
  		args += (name -> APIArgsTypeDelegate.ValueInstance(value, ',', strType))
  	}
  	override def toString = args.toString
}

object APIArgumentFactoryDispatch {
	def ArgsInstance(name: String, elem: scala.xml.Node) : APIArgumentsBase = {
		name match {
		  case "Route" => APIRouteArgumentsFactory.ArgsInstance(elem)
		  case "JOSN-RPC" => APIJOSNRPCArgumentsFactory.ArgsInstance(elem)
		  case "SplunkApiQuery" => APISplunkArgumnetsFactory.ArgsInstance(elem)
		  case _ => null
		}
	} 
}

object APIArgsTypeDelegate {
	def ValueInstance(value: String, split: Char, strType: String) : Any = {
		strType match {
		  case "String" => value
		  case "StringList" => new APIArgsStringListHelper(value.split(split).toList)
		  case "IntList" => new APIArgsIntListHelper(value.split(split).toList)
		  case "Int" => value.toInt
		  case _ => {
		    println("Argument type not found")
		    null
		  }
		}
	}
}

case class APIArgsStringListHelper(ls: List[String]) {
    override def toString = {
      	var re = "["
		for (item <- ls) re += "\"%s\",".format(item.toString)
		re.subSequence(0, re.length() - 1) + "]"
    }
}

case class APIArgsIntListHelper(ls: List[String]) {
    override def toString = {
      	var re = "["
		for (item <- ls) re += "%s,".format(item.toString)
		re.subSequence(0, re.length() - 1) + "]"
    }
}