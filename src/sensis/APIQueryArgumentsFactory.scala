/**
 * One web server is using JOSN-RPC
 * building a strategy to dynamic generate API query arguments
 * Created By Alfred Yang
 * 2nd April, 2014
 */

package sensis

import java.net.URLEncoder._
import scala.xml.Node

abstract class APIArgumentsBase {
	def AddArg(name: String, value: String)
	def toString : String
}

abstract class APIArgumentFactory {
	def ArgsInstance(elem: scala.xml.Node) : APIArgumentsBase
}

object APIRouteArgumentsFactory extends APIArgumentFactory {
	def ArgsInstance(elem: scala.xml.Node) = {
		var result = new APIWebServiceRouteArguments 
		val args = (elem \ "arg").map { ag =>
			result.AddArg((ag \ "@name"). text, (ag \ "@value").text)
		}
		result
	}
}

class APIWebServiceRouteArguments extends APIArgumentsBase {
  	case class APIRouteArg(name: String, originValue: String) {
  		override def toString = "&" + name + "=%s".format(encode(originValue, "UTF-8"))
  	}
  	
  	private var args : List[APIRouteArg] = Nil
	override def AddArg(name: String, value: String) = {
  		args = new APIRouteArg(name, value)::args
  	}
  	
  	override def toString = {
  		var r = ""
  		for (arg <- args) r += arg.toString
  		r
  	}
}

object APIArgumentFactoryDispatch {
	def ArgsInstance(name: String, elem: scala.xml.Node) : APIArgumentsBase = {
		name match {
		  case "Route" => APIRouteArgumentsFactory.ArgsInstance(elem)
		  case _ => null
		}
	} 
}