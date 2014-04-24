package sensis.DBClient

import java.util.Date
import java.util.GregorianCalendar

import scala.util.parsing.json.JSON

import org.joda.time.DateTime

import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.commons.MongoDBObject

import sensis.DBClient.DAO.CallsPerUser
import sensis.DBClient.DAO.DataSource
import sensis.DBClient.DAO.User

abstract class DataHandlerFacade {

  def saveCollectionToDB(userList: List[User], dbCollectionName: String)
  //def retriveCollection(dbCollectionName: String): List[User]
}

class DataBaseHandler extends DataHandlerFacade {

  def saveCollectionToDB(userList: List[User], dbCollectionName: String) {

    val iterator = userList.iterator
    // Getting data collection from DB
    val conn = DBConnectionFactory.dbConnection()
    val dataCollection = conn("SensisSAPIdb")(dbCollectionName)

    while (iterator.hasNext) {
      val loggedUser: User = iterator.next

      // Inserting data to DB collection
      var newMap: Map[String, Any] = Map.empty

      loggedUser.metricesMap.foreach(x => {
        if ((x._1).contains(".")) {
          newMap += (x._1.replace(".", "_") -> x._2)
        } else
          newMap += (x._1 -> x._2)
      })

      dataCollection.insert(buildSplunkDBObj(loggedUser, newMap).asDBObject)
    }
  }

  def buildSplunkDBObj(loggedUser: User, newMap: Map[String, Any]): MongoDBObject = {

    var insertObj: MongoDBObject = new MongoDBObject()

    insertObj += ("days" -> (loggedUser.days).asInstanceOf[Object])
    insertObj += ("key" -> loggedUser.userKey)
    for ((key, value) <- newMap)
      insertObj += (key -> (value).asInstanceOf[Object])

    insertObj
  }

  def retriveUserCalls(startDate: Date, endDate: Date, logSourceName: String): Map[String, Int] = {

    // Retrieving data collection from DB
    val conn = DBConnectionFactory.dbConnection()
    var dataCollection: MongoCollection = null
    // Specifying the collection according to the log source required.
    logSourceName match {
      case "Splunk" => dataCollection = conn("SensisSAPIdb")("splunkdata")
      case _ => throw new Exception
    }

    val daysForStart: Long = (new GregorianCalendar(startDate.getYear(), startDate.getMonth(), startDate.getDay()).getTime())
      .getTime() / (24 * 60 * 60 * 1000)
    val daysForEnd: Long = (new GregorianCalendar(endDate.getYear(), endDate.getMonth(), endDate.getDay()).getTime())
      .getTime() / (24 * 60 * 60 * 1000)

    for (dataRow <- dataCollection) {
      val parsedVal = JSON.parseFull(dataRow.toString())

      parsedVal match {
        case Some(parsedVal) => {
          var numOfCalls: Int = 0
          var userKey: String = ""

          for ((key, value) <- parsedVal.asInstanceOf[Map[String, Any]]) {
            key match {
              case "key" => userKey = value.toString
              case "_id" =>
              case "days" =>
              case _ => numOfCalls += (value.asInstanceOf[Double]).toInt
            }
          }
          CallsPerUser.ucMap += (userKey -> numOfCalls)
          println((userKey -> numOfCalls))
        }
        case None => throw new Exception
      }
    }
    
    CallsPerUser.ucMap
  }

  def getDataSource(logSourceName: String): DataSource = {
    var ds: DataSource = null

    val conn = DBConnectionFactory.dbConnection()
    val dsCollection = conn("SensisSAPIdb")("dataSource")

    for (dataRow <- dsCollection) {
      val parsedValue = JSON.parseFull(dataRow.toString())

      parsedValue match {
        case Some(parsedValue) => {
          val dsMap: Map[String, Any] = parsedValue.asInstanceOf[Map[String, Any]]

          if (dsMap("dsName").equals(logSourceName))
            ds = new DataSource((dsMap("_id").asInstanceOf[Map[String, Any]])("$oid").toString,
              dsMap("dsKey").toString,
              dsMap("dsName").toString,
              DateTime.now())
        }
        case None => throw new Exception
      }
    }
    ds
  }
}