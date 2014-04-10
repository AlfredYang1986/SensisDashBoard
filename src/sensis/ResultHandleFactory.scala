package sensis

import resultphrase._

object ResultHandleFactory {
	def getResultHandle(name: String) : ResultHandle = name match {
	  case "SplunkXML" => XMLResultHandle
	  case "MasheryJSON" => PrintlnResultHandle
	  case _ => null
	}
}