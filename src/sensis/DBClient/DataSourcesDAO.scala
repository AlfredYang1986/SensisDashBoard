package sensis.DBClient

import com.mongodb.casbah.MongoConnection
import scala.util.parsing.json.JSON
import scala.collection.immutable.List
import errorreport.Error_PhraseJosn
import scala.util.control.Breaks

object DataSourcesDAO {

  def dataSourceList: List[Map[String, String]] = List()

  def insertDataSource {

  }

  def updateDataSource {

  }

  def getData(keys: List[String]): List[Map[String, Any]] = {
    // Getting data collection from DB
    val conn = DBConnectionFactory.dbConnection()
    val dataCollection = conn("SensisSAPIdb")("dataSources")

    // Empty list that'll be returned
    var parsedDataList: List[Map[String, Any]] = List()

    for (dataRow <- dataCollection) {
      val parsedVal = JSON.parseFull(dataRow.toString())

      parsedVal match {
        
        case Some(parsedVal) => {
          val mp = parsedVal.asInstanceOf[Map[String, Any]]
          
          Breaks.breakable {
            for (key <- keys) {
              if (mp.contains(key)) {
                parsedDataList = mp :: parsedDataList
                Breaks.break
              }
            }
          }
        }
        case None => throw Error_PhraseJosn
      }
      parsedDataList.foreach(println)
    }

    null
  }

}