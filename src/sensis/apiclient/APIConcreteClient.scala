/**
 * Design Pattern, Proxy
 * Various API Service, when call shall provide name, url, and key
 * each Concrete Proxy shall be Singleton
 * Created by Afred yang
 * 29th March, 2014
 */

package sensis.apiclient

import scala.xml._
import io.Source

object APIConcreteProxyDemo extends APIAbstractProxy with APIProxy {
	def name = "Alfred Yang's Demo"
}

object MasheryProxy extends APIAbstractProxy with APIProxy {
	def name = "Mashery Proxy"
	override def request(url: String, key: String, args: String) : String = {
		val query = url + key
		Source.fromURL(query).mkString
	}
}