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
import java.util.Date

object SplunkProxy extends APIProxy {
	override def request(url: String, key: APIKeyBase, args: APIArgumentsBase) = {
		val date_format : SimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy:HH:mm:ss")
		val service = Service.connect(SplunkKey.loginArgs)
		
		def getStartDate(duration : String) : Date = duration match {
		  case "test" => date_format.parse("03/31/2014:03:00:00")
		  case "month" => val cal = Calendar.getInstance(); cal.setTime(new Date()); cal.add(Calendar.MONTH, -1); cal.getTime
		  case _ => throw Error_PhraseXML
		}
		
		def getEndDate(duration : String) : Date = duration match {
		  case "test" => date_format.parse("04/07/2014:03:00:00")
		  case "month" => val cal = Calendar.getInstance(); cal.setTime(new Date()); cal.getTime
		  case _ => throw Error_PhraseXML
		}
		
		def loopTimeSpan(st: Date, ed: Date) = {
			var cal : Calendar = Calendar.getInstance()
			cal.setTime(st)
			// get data minutes by minutes
			var it_time = st.getTime()
			while (it_time < ed.getTime()) {
				val begin = cal.getTime()
				cal.add(Calendar.MINUTE, 1)
				val end = cal.getTime()
				val s: String = "search earliest=\"" + date_format.format(begin) + "\" latest=\"" + date_format.format(end) + "\""
				val result = IOUtils.toString(service.export(s))
				callback(result)
				it_time = end.getTime()
			}
		}
		
		key match {
		  case SplunkKey => 
			val duration = args.asInstanceOf[APISplunkArguments].args.get("duration")
			.get.asInstanceOf[String];loopTimeSpan(getStartDate(duration), getEndDate(duration))
		  case _ => throw Error_PhraseXML
		}
	}
}