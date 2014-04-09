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
import sensis.APIArgumentsBase
import sensis.APIKeyBase

trait APIProxy {
	def request(url: String, key: APIKeyBase, args: APIArgumentsBase) : String = {
		val query = url + key.toString + args.toString
		Source.fromURL(query).mkString
	}
}

abstract class APIAbstractProxy {
	protected def name : String
}