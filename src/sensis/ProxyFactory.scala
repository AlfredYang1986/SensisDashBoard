/**
 * Proxy Factory
 * Created by Alfred Yang
 * 2nd April, 2014
 */

package sensis

import sensis.apiclient._

object ProxyFactory {
	def getAPIProxy(name: String) : APIProxy = name match {
	  case "Mashery" => APIConcreteProxyDemo
	  case "APIConcreteProxyDemo" => APIConcreteProxyDemo
	  case _ => null
	}
}