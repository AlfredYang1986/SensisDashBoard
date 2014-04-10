/**
 * Dynamic Generating Server query
 * Created by Alfred Yang
 * 2nd April, 2014
 */

package sensis

import sensis.apiclient.APIProxy
import resultphrase.ResultHandle

case class APIServer(name: String, url: String, key: APIKeyBase) {
	private var queries : List[APIQuery] = Nil
	private var concrete : APIProxy = null
	private var handle : ResultHandle = null
	
	def queryAll: Unit = {
	  	for(q <- queries) handle(q.queryServer)
	}

	def setProxy(p : APIProxy) = concrete = p
	def setHandle(h : ResultHandle) = handle = h
	
	def newQuery(args: APIArgumentsBase) = queries = new APIQuery(args)::queries

	case class APIQuery(args: APIArgumentsBase) {
		
	  	private def queryArgs : String = {
	  	  	args.toString()
		}
		
		def queryServer : String = {
			if (concrete != null) concrete.request(url, key, args)
			else { 
			  println("error, set proxy first") 
			  null 
			}
		}
		
		override def toString = {
	  	  	queryArgs
		}
	}
}
