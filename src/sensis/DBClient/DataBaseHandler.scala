package sensis.DBClient

import scala.util.parsing.json.JSON
import org.joda.time.DateTime
import com.mongodb.casbah.MongoCollection
import errorreport.Error_PhraseJosn
import sensis.DBClient.DAO.DataSource
import sensis.DBClient.DAO.User
import com.mongodb.casbah.commons.MongoDBObject

abstract class DataHandlerFacade {

  def saveCollectionToDB(userList: List[User], dbCollectionName: String)
  def retriveCollection(dbCollectionName: String): List[User]
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
        println(x + "   " + x._1 + "   " + x._2)
      })
      dataCollection.insert(MongoDBObject("key" -> loggedUser.userKey, "metrics" -> newMap, "updatedDate" -> DateTime.now()))
    }
  }

  def retriveCollection(logSourceName: String): List[User] = {
    // Retrieving data collection from DB
    val conn = DBConnectionFactory.dbConnection()
    var dataCollection: MongoCollection = null
    // Specifying the collection according to the log source required.
    logSourceName match {
      case "Splunk" => dataCollection = conn("SensisSAPIdb")("SplunkData")
      case _ => throw new Exception
    }

    var userList: List[User] = List.empty

    for (dataRow <- dataCollection) {
      // Parsing JSON data record
      val parsedValue = JSON.parseFull(dataRow.toString());

      parsedValue match {
        // Check whether the parse value is not empty
        case Some(parsedValue) => {
          logSourceName match {
            // Call the relevant method according to the data source.
            case "Splunk" => {
              var newUser: User = retrieveSplunkUser(parsedValue, logSourceName)
              if (newUser != null)
                userList = newUser :: userList
            }
            case _ => userList
          }
        }
        case None => throw Error_PhraseJosn
      }
    }
    userList
  }

  def retrieveSplunkUser(parsedValue: Any, logSourceName: String): User = {

    val uMap = parsedValue.asInstanceOf[Map[String, Any]]
    var newUser: User = new User(uMap("key").toString, uMap("metrics").asInstanceOf[Map[String, Any]])
    newUser.ds = getDataSource(logSourceName)

    if (newUser.ds == null)
      newUser.ds = DataSource(null, null, null, null)

    newUser
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