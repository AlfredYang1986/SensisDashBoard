/**
 * one day data insert once
 * Created by Alfred Yang
 * 14th May, 2014
 */

package cache

object SplunkDatabaseName {
	def splunk_raw_data = "splunk_data_%d"
	def splunk_end_point = "splunk_end_points"
	def splunk_query_data = "splunk_query_%d"
	def splunk_request_data = "splunk_request_%d"
}

object MasheryDatabaseName {
  	def mashery_data = "masherydata"
  	def mashery_summary = "mashery_summary_%d"
}
