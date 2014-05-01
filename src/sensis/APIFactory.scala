/**
 * Factory Pattern
 * Call the different Proxy to call different service
 * Base on the Services.xml
 * 		The Services.xml shall provide url, key, and call arguments
 */

package sensis

import scala.xml._
import sensis.apiclient._
import resultphrase._

object APIFactory {
	def apply() = {
		def xmlPath : String = "StructFile/Services.xml"
		def service_xml_pharse(path: String) : scala.xml.Elem = {
			val doc = scala.xml.XML.loadFile(path)
			if (doc == null) {
				println("Error !! No such file")
				null
			}
			println("Found the XML file")
			doc
		}
		
		def getKeyInstance(sk : Node, fn : String) : APIKeyBase = {
		  	val keyInstance = APIServiceKeyDispatchFactory.APIKeyDispatch(fn)
		  	(sk \ "arg").map { ka =>
		  	  	keyInstance.AddKeyArg((ka \ "@name").text, (ka \ "@value").text)
		  	  }
		  	(sk \ "urlName").map { un =>
		  	  	keyInstance.AppendUrlString(
		  	  	    (un \ "@name").text, (un \ "@delegate").text, keyInstance.args)
		  	  }
		  	keyInstance
		}

		def getServer(name: String, url: String, key : APIKeyBase, se : Node) : APIServer = {
		  	val re = new APIServer((se \ "@name").text, (se \ "@url").text, key)
		  	// for query
		  	(se \ "query").map { qu =>
		  	  	val name = (qu \ "@factory").text
		  	  	val args = APIArgumentFactoryDispatch.ArgsInstance(name, qu)
		  		re.newQuery(args)
		  	}
		  	re.setProxy(getAPIProxy(re.name))
		  	re.setHandle(getResultHandle((se \ "@resultHandle").text))
		  	re
		}

		def getAPIProxy(name: String) : APIProxy = name match {
			case "Mashery" => MasheryProxy
			case "APIConcreteProxyDemo" => APIConcreteProxyDemo
			case "Mashery-Sandbox" => MasheryProxy
			case "Splunk" => SplunkProxy
			case _ => null
		}

		def getResultHandle(name: String) : ResultHandle = name match {
			case "SplunkXML" => XMLResultHandle
			case "MasheryJSON" => PrintlnResultHandle
			case _ => null
		}
		
		for (se <- ((service_xml_pharse(xmlPath) \\ "Service").map { se =>
		  	getServer((se \ "@name").text, (se \ "@url").text
		  	    , getKeyInstance((se \ "key").apply(0)
		  	    , (se \ "key" \ "@factory").text), se)
		})) se.queryAll
	}
}