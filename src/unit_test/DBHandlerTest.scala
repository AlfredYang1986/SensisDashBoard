package unit_test

import sensis.DBClient.DataBaseHandler
import sensis.DBClient.dataHandlerFacade

object DBHandlerTest extends App {

  val dbh: dataHandlerFacade = new DataBaseHandler

  //dbh.saveCollectionToDB(List.empty)
  
  dbh.retriveCollection("SplunkData")

}