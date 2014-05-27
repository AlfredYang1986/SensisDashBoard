package query.helper

import query._data_connection
import cache.SplunkDatabaseName

class CollectionHelper {

  def deleteCollections(start: Int, end: Int) {
    for (day <- start to end) {
      _data_connection.getCollection(SplunkDatabaseName.splunk_raw_data.format(day)).drop
      _data_connection.getCollection(SplunkDatabaseName.splunk_query_data.format(day)).drop
    }
  }

  def archiveCollections(start: Int, end: Int) = ???

}