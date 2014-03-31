package sensis

import sensis.apiclient.APIProxy
import java.net.URLEncoder._

case class APIQueryArgsBase(name: String, originValue: String) {
	override def toString = "&" + name + "=%s".format(encode(originValue, "UTF-8"))
}

case class APIServer(name: String, url: String, key: String) {
	private var queries : List[APIQuery] = Nil
	private var concrete : APIProxy = null
	
	def queryAll: Unit = {
	  	for(q <- queries) println(q.queryServer)
	}

	def setProxy(p : APIProxy) = concrete = p
	
	def newQuery(args: List[APIQueryArgsBase]) = queries = new APIQuery(args)::queries

	case class APIQuery(args: List[APIQueryArgsBase]) {
		
	  	private def queryArgs : String = {
		  	var r : String = ""
		  	for (arg <- args) r += arg.toString
		  	r
		}
		
		def queryServer : String = {
			if (concrete != null) concrete.request(url, key, queryArgs)
			else { 
			  println("error, set proxy first") 
			  null 
			}
		}
	}
}
