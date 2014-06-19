/**
 * Splunk Proxy
 * for grap the data from splunk server 
 * this is different from others, it use splunk api not http protocol
 * Created By Alfred Yang
 * 10th April 2014
 */

package sensis.apiclient

import sensis.APIArgumentsBase
import sensis.APIKeyBase
import sensis.APISplunkArguments
import sensis.SplunkKey
import errorreport.Error_PhraseXML
import java.text.SimpleDateFormat
import com.splunk.Service
import java.util.Date
import java.util.Calendar

object SplunkProxy extends SplunkProxyBase {
  
  	def index : String = """index="ssapi_prod""""
	def source_type : String = "sourcetype=access_combined"
  	override def getStartDate(duration : String) : Date = duration match {
		case "test" => date_format.parse("03/31/2014:03:00:00")
		case _ => super.getStartDate(duration)
	}
		
	override def getEndDate(duration : String) : Date = duration match {
		case "test" => date_format.parse("04/07/2014:03:00:00")
		case _ => super.getEndDate(duration)
	}
  
	override def request(url: String, key: APIKeyBase, args: APIArgumentsBase) = {
		callback("start")
		callback("property")
		val ek = args.asInstanceOf[APISplunkArguments].args.get("external").get.asInstanceOf[String]
		callback(ek)
		callback("store")
		super.request(url, key, args)
		callback("end")
//		callback("none")
	}
}
