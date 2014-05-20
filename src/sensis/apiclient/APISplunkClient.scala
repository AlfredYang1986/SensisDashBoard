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
import com.splunk.JobArgs
import com.splunk.JobResultsPreviewArgs

object SplunkProxy extends APIProxy {
	override def request(url: String, key: APIKeyBase, args: APIArgumentsBase) = {
		val date_format : SimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy:HH:mm:ss")
		val service = Service.connect(SplunkKey.loginArgs)
		
		def getStartDate(duration : String) : Date = duration match {
		  case "test" => date_format.parse("03/31/2014:03:00:00")
//		  case "test" => date_format.parse("04/01/2014:03:00:00")
		  case "month" => val cal = Calendar.getInstance(); cal.setTime(new Date()); cal.add(Calendar.MONTH, -1); cal.getTime
		  case _ => throw Error_PhraseXML
		}
		
		def getEndDate(duration : String) : Date = duration match {
		  case "test" => date_format.parse("04/07/2014:03:00:00")
//		  case "test" => date_format.parse("04/01/2014:04:00:00")
		  case "month" => val cal = Calendar.getInstance(); cal.setTime(new Date()); cal.getTime
		  case _ => throw Error_PhraseXML
		}
		
		def HandleSplunkDateWithJobs(begin : Date, end : Date) : Unit = {
			println("Start")
			val search = "search earliest=\"" + date_format.format(begin) + "\" latest=\"" + date_format.format(end) + "\""
	  
			// Create an argument 
			val jobArgs = new JobArgs
			jobArgs.setExecutionMode(JobArgs.ExecutionMode.NORMAL)
	
			// Create a Job
			val job = service.search(search, jobArgs)
			job.enablePreview()
			job.update()
	
			while (!job.isReady()) Thread.sleep(500)
	
			var countBatch = 0;
			while (!job.isDone()) {
				countBatch = countBatch + 1
				val previewargs = new JobResultsPreviewArgs 
				previewargs.setCount(500) // Get 500 preview at a time
				previewargs.setOutputMode(JobResultsPreviewArgs.OutputMode.XML)
		
				val results =  job.getResultsPreview(previewargs)
		
				println(IOUtils.toString(results))
				println("Times: countBatch =  %d".format(countBatch))
			}
			println("End")
		}
		
		def HandleSplunkDate_safe(begin : Date, end : Date) : Unit = {
			val s: String = "search earliest=\"" + date_format.format(begin) + "\" latest=\"" + date_format.format(end) + "\""
			
			try {
				callback(IOUtils.toString(service.export(
				    "search earliest=\"" + date_format.format(begin) + "\" latest=\"" + 
				    date_format.format(end) + "\"")))
			} catch {
			  case ex : OutOfMemoryError => HandleSplunkDateWithJobs(begin, end)
			}
		}
	
		def loopTimeSpan(st: Date, ed: Date) = {
			def presentage(n: Date) : Double = (n.getTime() - st.getTime()) * 100.0 / (ed.getTime() - st.getTime())
			
			var cal : Calendar = Calendar.getInstance()
			cal.setTime(st)
			// get data minutes by minutes
			var it_time = st.getTime()
			var result : String = ""
			while (it_time < ed.getTime()) {
				result = ""
				val begin = cal.getTime()
				cal.add(Calendar.MINUTE, 1)
				val end = cal.getTime()
				println(presentage(end))
				HandleSplunkDate_safe(begin, end)
				it_time = end.getTime()
			}
		}

		callback("start")
		callback("property")
		val ek = args.asInstanceOf[APISplunkArguments].args.get("external").get.asInstanceOf[String]
		callback(ek)
		callback("store")
		key match {
		  case SplunkKey => 
			val duration = args.asInstanceOf[APISplunkArguments].args.get("duration")
			.get.asInstanceOf[String];loopTimeSpan(getStartDate(duration), getEndDate(duration))
		  case _ => throw Error_PhraseXML
		}
		callback("end")
//		callback("none")
	}
}
