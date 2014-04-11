/**
 * Splunk Proxy
 * for grap the data from splunk server 
 * this is different from others, it use splunk api not http protocol
 * Created By Alfred Yang
 * 10th April 2014
 */

package sensis.apiclient

import sensis.APIKeyBase
import sensis.APIArgumentsBase
import errorreport.Error_PhraseXML
import com.splunk.Service
import org.apache.commons.io.IOUtils
import sensis.SplunkKey
import sensis.APISplunkArguments
import java.util.Calendar
import java.text.SimpleDateFormat

object SplunkProxy extends APIProxy {
	override def request(url: String, key: APIKeyBase, args: APIArgumentsBase) {
		key match {
		  case SplunkKey => {
			val service : Service = Service.connect(SplunkKey.loginArgs)
//			val duration: String = args.asInstanceOf[APISplunkArguments].args.get("duration").get.asInstanceOf[String]
			val date_format : SimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy:HH:mm:ss")
			// for test
			val now_date : java.util.Date = date_format.parse("04/07/2014:03:00:00")
			val early_data : java.util.Date = date_format.parse("03/31/2014:04:00:00")
			var cal : Calendar = Calendar.getInstance()
			cal.setTime(early_data)
//			val now_date : java.util.Date = new java.util.Date()
//			var cal : Calendar = Calendar.getInstance()
//			cal.add(Calendar.MONTH, -1)
//			var early_data : java.util.Date = cal.getTime()
			// get data minutes by minutes
			var it_time = early_data.getTime()
			while (it_time < now_date.getTime()) {
				val begin = cal.getTime()
				cal.add(Calendar.MINUTE, 1)
				val end = cal.getTime()
				val s: String = "search earliest=\"" + date_format.format(begin) + "\" latest=\"" + date_format.format(end) + "\""
				val result = IOUtils.toString(service.export(s))
				callback(result)
				it_time = end.getTime()
			}
			
//			IOUtils.toString(service.export("search earliest=\"04/04/2014:00:00:00\" latest=\"04/04/2014:00:00:59\""))
		  }
		  case _ => throw Error_PhraseXML
		}
	}
}