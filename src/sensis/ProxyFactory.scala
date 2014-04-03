/**
 * Proxy Factory
 * Created by Alfred Yang
 * 2nd April, 2014
 */

package sensis

import sensis.apiclient._

object ProxyFactory {
	def getAPIProxy(name: String) : APIProxy = name match {
	  case "Mashery" => MasheryProxy
	  case "APIConcreteProxyDemo" => APIConcreteProxyDemo
	  case "Mashery-Sandbox" => MasheryProxy
	  case _ => null
	}
}