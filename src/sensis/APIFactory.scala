/**
 * Factory Pattern
 * Call the different Proxy to call different service
 * Base on the Services.xml
 * 		The Services.xml shall provide url, key, and call arguments
 */

package sensis

import scala.xml._
import sensis.apiclient._

object APIFactory {

	def xmlPath : String = "/Users/yangyuan/Desktop/Scala/RMIT2014/StructFile/Services.xml"
  
	private def service_xml_pharse(path: String) : scala.xml.Elem = {
		val doc = scala.xml.XML.loadFile(path)
		if (doc == null) {
			println("Error !! No such file")
			null
		}
		println("Found the XML file")
		doc
	}
  
	def apply() : String = {
		val elem = service_xml_pharse(xmlPath)
		val servers = (elem \\ "Service").map { se =>
		  	// for Server
		  	val service_name = (se \ "@name").text
		  	val service_url = (se \ "@url").text
		  	val service_key = (se \ "@key").text
		  	
		  	var re = new APIServer(service_name, service_url, service_key)
		  	// for query
		  	(se \ "query").map { qu =>
		  		// for query arguments
		  	  	val name = (qu \ "@factory").text
		  	  	val args = APIArgumentFactoryDispatch.ArgsInstance(name, qu)
		  		re.newQuery(args)
		  	}
		  	re.setProxy(ProxyFactory.getAPIProxy(re.name))
		  	re
		}
		
		for (se <- servers) se.queryAll
		null
	}
}