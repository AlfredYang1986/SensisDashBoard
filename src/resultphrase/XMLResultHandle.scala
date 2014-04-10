package resultphrase

import scala.xml.XML._

object XMLResultHandle extends ResultHandle {
	def apply(result: String) = {
		val s_beg : String = "<root>"
		val s_end : String = "</root>"
		var r = s_beg + result.substring(result.indexOf("?>") + 2) + s_end
		val h: scala.xml.Elem = scala.xml.XML.loadString(r)
		(h \\ "result").map { index =>
		  	(index \ "field").map { field =>
		  	  	val k = (field \ "@k").text
		  	  	if (k == "_raw") {
		  	  		println(field.text)
		  	  	}
		  	}
		}
	}
}