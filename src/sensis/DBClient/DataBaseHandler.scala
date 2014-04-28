package sensis.DBClient

import sensis.DBClient.DAO.User
import scala.util.parsing.json.JSON
import scala.util.parsing.json._
import org.jacoco.core.internal.data.CompactDataInput
import javax.annotation.Generated
import com.mongodb.casbah.commons.MongoDBObject
import errorreport.Error_PhraseJosn
import scala.collection.mutable.ArrayBuffer


abstract class dataHandlerFacade {

  def saveCollectionToDB(userList: List[User], dbCollectionName: String)
  def retriveCollection(dbCollectionName: String): List[User]
}

class DataBaseHandler extends dataHandlerFacade {

  def saveCollectionToDB(userList: List[User], dbCollectionName: String) {

    val iterator = userList.iterator
    // Getting data collection from DB
    val conn = DBConnectionFactory.dbConnection()
    val dataCollection = conn("SensisSAPIdb")(dbCollectionName)

    while (iterator.hasNext) {

      val loggedUser: User = iterator.next
      // Inserting data to DB collection
     val shit =new ArrayBuffer[String]()
     
   val cao:String=loggedUser.metricesMap.keySet.toString.replace(".", "_")
   val strarray:Array[String] = cao.split(",");
   val caodan:String=loggedUser.metricesMap.values.toString
   val strarray2 = caodan.split(",");
   

   //+ loggedUser.metricesMap.values
        
      dataCollection.insert(MongoDBObject("key" -> loggedUser.key, "metrice" ->map ))
    }
  }

  def retriveCollection(dbCollectionName: String): List[User] = {

    // Retriving data collection from DB
    val conn = DBConnectionFactory.dbConnection()
    val dataCollection = conn("SensisSAPIdb")(dbCollectionName)

    var userList: List[User] = List.empty

    for (dataRow <- dataCollection) {
      val parsedValue = JSON.parseFull(dataRow.toString());

      parsedValue match {

        case Some(parsedValue) => {
          val uMap = parsedValue.asInstanceOf[Map[String, Any]]
          var newUser: User = new User(uMap("key").toString, uMap("metrics").asInstanceOf[Map[String, Any]])
                   
          userList = newUser :: userList
        }
        case None => throw Error_PhraseJosn
      }
    }
    println(userList)
    userList
  }
}