package sensis

import sensis.apiclient._

object ProxyFactory {
	def getAPIProxy(name: String) : APIProxy = name match {
	  case "APIConcreteProxyDemo" => APIConcreteProxyDemo
	  case _ => null
	}
}