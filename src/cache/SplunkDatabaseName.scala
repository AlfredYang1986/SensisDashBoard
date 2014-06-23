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
  def splunk_query_only_data = "splunk_query_only_%d"
  def splunk_location_only_data = "splunk_location_only_%d"
  def splunk_request_data = "splunk_request_%d"
}

object MasheryDatabaseName {
  def mashery_data = "masherydata"
  def mashery_summary = "mashery_summary_%d"
}

object SearchQualityDBName {
  def search_quality_data = "search_quality_data"
  def search_quality_columns = Array("days", "SAPI", "SAPI_Comment", "Yellow", "Yellow_Comment", "One_Search", "One_Search_Comment")
  def evaluation_matric_data = "eval_matric_data"
  def evaluation_matric_columns = Array("Name_Search", "Name_Search_Comment", "Type_Search", "Type_Search_Comment", "Concept_Recall", "Concept_Recall_Comment", "Duplicates", "Duplicates_Comment", "Zero_Results", "Zero_Results_Comment")
}
