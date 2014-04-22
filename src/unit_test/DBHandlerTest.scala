package unit_test

import sensis.DBClient.DataBaseHandler
import sensis.DBClient.DataHandlerFacade

object DBHandlerTest extends App {

  val dbh: DataHandlerFacade = new DataBaseHandler
  val dbh2: DataBaseHandler = new DataBaseHandler

//  dbh.saveCollectionToDB(List.empty, "SplunkData")
  
  dbh.retriveCollection("Splunk")
//  dbh2.getDataSource("New Relic")

}