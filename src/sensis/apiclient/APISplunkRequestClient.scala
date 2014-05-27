package sensis.apiclient

import sensis.APIKeyBase
import sensis.APIArgumentsBase
import sensis.APISplunkArguments
import java.text.SimpleDateFormat
import java.util.Date

object SplunkRequestProxy extends SplunkProxyBase {
		
	def source_type : String = "sourcetype=request.log"
	override def getStartDate(duration : String) : Date = duration match {
		case "test" => date_format.parse("05/13/2014:03:35:00")
		case _ => super.getStartDate(duration)
	}
		
	override def getEndDate(duration : String) : Date = duration match {
		case "test" => date_format.parse("05/14/2014:03:15:00")
//		case "test" => date_format.parse("05/13/2014:04:15:00")
		case _ => super.getEndDate(duration)
	}
	
	override def request(url: String, key: APIKeyBase, args: APIArgumentsBase) = {
		callback("start")
		super.request(url, key, args)
		callback("end")
	}
}