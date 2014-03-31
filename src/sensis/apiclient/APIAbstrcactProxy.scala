/**
 * Design Pattern, Proxy
 * Provide an universal way to call the sensis API
 * Write once can call all the API in different service
 * One one service is not provide API, which should override the trait function
 * 
 * Created by Alfred Yang
 * 29th March, 2014
 */

package sensis.apiclient

import io.Source
import java.net.URLEncoder._
import scala.util.parsing.json._

trait APIProxy {
	def request(url: String, key: String, args: String) : String = {
		val query = url + "key=%s".format(key) + args
		Source.fromURL(query).mkString
	}
}

abstract class APIAbstractProxy {
	protected def name : String
}