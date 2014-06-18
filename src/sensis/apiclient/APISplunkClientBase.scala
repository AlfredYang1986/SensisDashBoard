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

abstract class SplunkProxyBase extends APIProxy {
  
	def source_type : String
	val date_format : SimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy:HH:mm:ss")
	val service = Service.connect(SplunkKey.loginArgs)
	
	val year_pattern_p = new scala.util.matching.Regex("""(\d+)y""", "num")
	val month_pattern_p = new scala.util.matching.Regex("""(\d+)m""", "num")
	val day_pattern_p = new scala.util.matching.Regex("""(\d+)d""", "num")
	val week_pattern_p = new scala.util.matching.Regex("""(\d+)w""", "num")
	
	def getStartDate(duration : String) : Date = duration match {
	  case day_pattern_p(num) => val cal = Calendar.getInstance(); cal.setTime(new Date()); cal.add(Calendar.DATE, -Integer.parseInt(num)); cal.getTime 
	  case month_pattern_p(num) => val cal = Calendar.getInstance(); cal.setTime(new Date()); cal.add(Calendar.MONTH, -Integer.parseInt(num)); cal.getTime 
	  case year_pattern_p(num) => val cal = Calendar.getInstance(); cal.setTime(new Date()); cal.add(Calendar.YEAR, -Integer.parseInt(num)); cal.getTime 
	  case week_pattern_p(num) => val cal = Calendar.getInstance(); cal.setTime(new Date()); cal.add(Calendar.WEEK_OF_YEAR, -Integer.parseInt(num)); cal.getTime 
	  case _ => throw Error_PhraseXML
	}
		
	def getEndDate(duration : String) : Date = duration match {
		case _ => val cal = Calendar.getInstance(); cal.setTime(new Date()); cal.getTime
	}
	
	def getSearchString(begin : Date, end : Date) : String = {
		"search %s earliest=\"" + date_format.format(begin) + "\" latest=\"" + date_format.format(end) + "\"".format(source_type)
	}
		
	def HandleSplunkDateWithJobs(begin : Date, end : Date) : Unit = {
		println("Start")
		val search = getSearchString(begin, end)
		
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
		val search = getSearchString(begin, end)
			
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
	
	override def request(url: String, key: APIKeyBase, args: APIArgumentsBase) = {
		key match {
		  case SplunkKey => 
			val duration = args.asInstanceOf[APISplunkArguments].args.get("duration")
			.get.asInstanceOf[String];loopTimeSpan(getStartDate(duration), getEndDate(duration))
		  case _ => throw Error_PhraseXML
		}
	}
}
