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

object SplunkProxy extends APIProxy {
	override def request(url: String, key: APIKeyBase, args: APIArgumentsBase) : String = {
		key match {
		  case SplunkKey => {
			val service : Service = Service.connect(SplunkKey.loginArgs)
			IOUtils.toString(service.export("search earliest=\"04/04/2014:00:00:00\" latest=\"04/04/2014:00:00:01\""))
		  }
		  case _ => throw Error_PhraseXML
		}
	}
}