/**
 * Design Pattern, Proxy
 * Various API Service, when call shall provide name, url, and key
 * each Concrete Proxy shall be Singleton
 * Created by Afred yang
 * 29th March, 2014
 */
package sensis.apiclient

import java.net.URL
import java.net.HttpURLConnection
import java.net.URLEncoder
import java.io.OutputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.IOException
import errorreport.Error_CallApiFail
import sensis.APIKeyBase
import sensis.APIArgumentsBase

object MasheryProxy extends APIAbstractProxy with APIProxy {
	def name = "Mashery Proxy"
	override def request(url: String, key: APIKeyBase, args: APIArgumentsBase) : String = {
		val query = url + key.UrlString
		val mUrl : URL = new URL(query)
		val urlConn : HttpURLConnection = mUrl.openConnection().asInstanceOf[HttpURLConnection]
		urlConn.setRequestMethod("POST");
        urlConn.setDoOutput(true);
        urlConn.setReadTimeout(10000);
	
		urlConn.addRequestProperty("Content-Type", "application/json")
		urlConn.addRequestProperty("Accept", "text/plain")
		urlConn.setRequestProperty("Content-Length", Integer.toString(args.toString().length()))

		urlConn.connect();
	
		var wr : OutputStreamWriter = new OutputStreamWriter(urlConn.getOutputStream());
        wr.write(args.toString)
        wr.flush()
		         
        var sb : StringBuilder = new StringBuilder();
        try {
        		var rd : BufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        		var line : String = ""
        		while (line != null) {
        			line = rd.readLine()
        			if (line != null) sb.append(line + '\n');
        		}
        } catch {
          case ex : IOException => println(ex.getMessage())
          case _ : Throwable => throw Error_CallApiFail
        } 
        sb.toString
	}
}