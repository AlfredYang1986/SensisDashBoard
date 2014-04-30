package sensis.DBClient

import java.text.SimpleDateFormat
import java.util.Date
import java.util.GregorianCalendar
import scala.collection.immutable.TreeMap
import scala.collection.mutable.LinkedList
import scala.util.parsing.json.JSON
import org.joda.time.DateTime
import com.mongodb.casbah.Imports.IntDoNOk
import com.mongodb.casbah.Imports.mongoQueryStatements
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.commons.MongoDBObject
import query.from
import sensis.DBClient.DAO.CallsPerUser
import sensis.DBClient.DAO.DataSource
import sensis.DBClient.DAO.SplunkDataDAO
import sensis.DBClient.DAO.User
import sensis.DBClient.DAO.SplunkDataDAO
import sensis.DBClient.DAO.SplunkDataDAO
import scala.collection.immutable.ListMap

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
    var funcColumnList: LinkedList[String] = LinkedList("getByListingId", "search", "serviceArea",
      "index/listingsInHeadingInLocality", "singleSearch", "report/appearance", "report/viewDetails",
      "index/topCategoriesInLocality", "index/topCategoriesInLocality", "index/localitiesInState")

    insertObj += ("days" -> (loggedUser.days).asInstanceOf[Object])
    insertObj += ("key" -> loggedUser.userKey)
    for (funcStr <- funcColumnList) {
      insertObj += (funcStr -> (newMap.getOrElse(funcStr, 0.asInstanceOf[Object])).asInstanceOf[Object])
    }
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

  /**
   * Retrieve a single user object with the function calls summed-up.
   */
  def getEachUserByKey(startDate: String, endDate: String, logSourceName: String, ukey: String): List[SplunkDataDAO] = {

    val start: Long = getLongDays(startDate)
    val end: Long = getLongDays(endDate)
    var distinctUsers: TreeMap[String, SplunkDataDAO] = TreeMap.empty

    var numOfCalls = from db () in "splunkdata" where ("days" $gte start.asInstanceOf[Int], "days" $lte end.asInstanceOf[Int], "key" -> ukey) select
      {
        x =>
          {
            val userKey = x.as[String]("key")
            var newDataObj: SplunkDataDAO = null

            if (distinctUsers.keys.exists(_ == userKey)) {
              val spDao: SplunkDataDAO = distinctUsers(userKey)

              newDataObj = new SplunkDataDAO(userKey, x.as[Long]("days"),
                (spDao.getByListingId + x.getAsOrElse("getByListingId", 0)),
                (spDao.search + x.getAsOrElse("search", 0)),
                (spDao.serviceArea + x.getAsOrElse("serviceArea", 0)),
                (spDao.listingsInHeadingInLocality + x.getAsOrElse("index/listingsInHeadingInLocality", 0)),
                (spDao.singleSearch + x.getAsOrElse("singleSearch", 0)),
                (spDao.appearance + x.getAsOrElse("report/appearance", 0)),
                (spDao.viewDetails + x.getAsOrElse("report/viewDetails", 0)),
                (spDao.topCategoriesInLocality + x.getAsOrElse("index/topCategoriesInLocality", 0)),
                (spDao.topCategoriesInLocality + x.getAsOrElse("index/topCategoriesInLocality", 0)),
                (spDao.localitiesInState + x.getAsOrElse("index/localitiesInState", 0)))
              distinctUsers += (userKey -> newDataObj)
            } else {
              newDataObj = new SplunkDataDAO(userKey, x.as[Long]("days"),
                x.getAsOrElse("getByListingId", 0),
                x.getAsOrElse("search", 0),
                x.getAsOrElse("serviceArea", 0),
                x.getAsOrElse("index/listingsInHeadingInLocality", 0),
                x.getAsOrElse("singleSearch", 0),
                x.getAsOrElse("report/appearance", 0),
                x.getAsOrElse("report/viewDetails", 0),
                x.getAsOrElse("index/topCategoriesInLocality", 0),
                x.getAsOrElse("index/topCategoriesInLocality", 0),
                x.getAsOrElse("index/localitiesInState", 0))
              distinctUsers += (userKey -> newDataObj)
            }
            newDataObj
          }
      }
    distinctUsers.values.toList
  }

  /**
   * Retrieve a list of distinct users keys.
   */
  def getAllDistinctUserKeys(startDate: String, endDate: String, logSourceName: String): List[String] = {
    var ukeyList: List[String] = List.empty
    getDistinctUsers(startDate, endDate, logSourceName).foreach(x => { ukeyList = x.UserKey :: ukeyList })
    ukeyList.foreach(println)
    ukeyList
  }

  /**
   * Retrieve distinct users with the function calls summed-up.
   */
  def getDistinctUsers(startDate: String, endDate: String, logSourceName: String): List[SplunkDataDAO] = {

    val start: Long = getLongDays(startDate)
    val end: Long = getLongDays(endDate)
    var distinctUsers: TreeMap[String, SplunkDataDAO] = TreeMap.empty

    var numOfCalls = from db () in "splunkdata" where ("days" $gte start.asInstanceOf[Int], "days" $lte end.asInstanceOf[Int]) select
      {
        x =>
          {
            val userKey = x.as[String]("key")
            var newDataObj: SplunkDataDAO = null

            if (distinctUsers.keys.exists(_ == userKey)) {
              val spDao: SplunkDataDAO = distinctUsers(userKey)

              newDataObj = new SplunkDataDAO(userKey, x.as[Long]("days"),
                (spDao.getByListingId + x.getAsOrElse("getByListingId", 0)),
                (spDao.search + x.getAsOrElse("search", 0)),
                (spDao.serviceArea + x.getAsOrElse("serviceArea", 0)),
                (spDao.listingsInHeadingInLocality + x.getAsOrElse("index/listingsInHeadingInLocality", 0)),
                (spDao.singleSearch + x.getAsOrElse("singleSearch", 0)),
                (spDao.appearance + x.getAsOrElse("report/appearance", 0)),
                (spDao.viewDetails + x.getAsOrElse("report/viewDetails", 0)),
                (spDao.topCategoriesInLocality + x.getAsOrElse("index/topCategoriesInLocality", 0)),
                (spDao.topCategoriesInLocality + x.getAsOrElse("index/topCategoriesInLocality", 0)),
                (spDao.localitiesInState + x.getAsOrElse("index/localitiesInState", 0)))
              distinctUsers += (userKey -> newDataObj)
            } else {
              newDataObj = new SplunkDataDAO(userKey, x.as[Long]("days"),
                x.getAsOrElse("getByListingId", 0),
                x.getAsOrElse("search", 0),
                x.getAsOrElse("serviceArea", 0),
                x.getAsOrElse("index/listingsInHeadingInLocality", 0),
                x.getAsOrElse("singleSearch", 0),
                x.getAsOrElse("report/appearance", 0),
                x.getAsOrElse("report/viewDetails", 0),
                x.getAsOrElse("index/topCategoriesInLocality", 0),
                x.getAsOrElse("index/topCategoriesInLocality", 0),
                x.getAsOrElse("index/localitiesInState", 0))
              distinctUsers += (userKey -> newDataObj)
            }
            newDataObj
          }
      }
    distinctUsers.values.toList
  }

  /**
   * Return the top ten distinct users.
   */
  def getTopTenUsers(startDate: String, endDate: String, logSourceName: String): List[SplunkDataDAO] = {
    sortForTopTen(getDistinctUsers(startDate, endDate, logSourceName))
  }

  /**
   * Provide the function usage for each distinct function, in descending order.
   */
  def getFunctionUsage(startDate: String, endDate: String, logSourceName: String) {

    var distinctUsers: List[SplunkDataDAO] = getDistinctUsers(startDate, endDate, logSourceName)
    var funcUsage: TreeMap[String, Int] = TreeMap.empty

    for (user <- distinctUsers) {
      funcUsage += ("getByListingId" -> (funcUsage.getOrElse("getByListingId", 0) + user.getByListingId))
      funcUsage += ("search" -> (funcUsage.getOrElse("search", 0) + user.search))
      funcUsage += ("serviceArea" -> (funcUsage.getOrElse("serviceArea", 0) + user.serviceArea))
      funcUsage += ("listingsInHeadingInLocality" -> (funcUsage.getOrElse("listingsInHeadingInLocality", 0) + user.listingsInHeadingInLocality))
      funcUsage += ("singleSearch" -> (funcUsage.getOrElse("singleSearch", 0) + user.singleSearch))
      funcUsage += ("appearance" -> (funcUsage.getOrElse("appearance", 0) + user.appearance))
      funcUsage += ("viewDetails" -> (funcUsage.getOrElse("viewDetails", 0) + user.viewDetails))
      funcUsage += ("topCategoriesInLocality" -> (funcUsage.getOrElse("topCategoriesInLocality", 0) + user.topCategoriesInLocality))
      funcUsage += ("categoriesInlocality" -> (funcUsage.getOrElse("categoriesInlocality", 0) + user.categoriesInlocality))
      funcUsage += ("localitiesInState" -> (funcUsage.getOrElse("localitiesInState", 0) + user.localitiesInState))
    }
    /* Sorting the function usage in descending order. */
    ListMap[String, Int](funcUsage.toList.sortWith { _._2 > _._2 }: _*).toMap[String, Int]
  }

  def getLongDays(date: String): Long = {
    val d: Date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date)
    val longDays: Long = (new GregorianCalendar(d.getYear(), d.getMonth(), d.getDay()).getTime()).getTime() / (24 * 60 * 60 * 1000)
    longDays
  }

  def sortForTopTen(numOfCalls: List[SplunkDataDAO]): List[SplunkDataDAO] = {
    var counter: Int = 0
    var arr: Array[SplunkDataDAO] = numOfCalls.toArray[SplunkDataDAO]

    for (j <- 0 to (numOfCalls.size)) {
      counter = 0
      for (i <- arr) {
        if ((counter < 33) && (i.getTotalFuncCalls < arr(counter + 1).getTotalFuncCalls)) {
          arr(counter) = arr(counter + 1)
          arr(counter + 1) = i
        }
        counter = counter + 1
      }
    }
    arr.toList.slice(0, 10)
  }

}