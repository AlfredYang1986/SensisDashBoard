/**
 * Design Pattern, Proxy
 * Various API Service, when call shall provide name, url, and key
 * each Concrete Proxy shall be Singleton
 * Created by Afred yang
 * 29th March, 2014
 */

package sensis.apiclient

object APIConcreteProxyDemo extends APIAbstractProxy with APIProxy {
	def name = "Alfred Yang's Demo"
}