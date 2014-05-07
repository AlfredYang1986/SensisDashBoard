package unit_test

import scala.util.parsing.json.JSONObject
import sensis.DBClient.DBConnectionFactory
import sensis.DBClient.DataBaseHandler
import sensis.DBClient.DataHandlerFacade
import scala.util.parsing.json.JSON
import com.mongodb.util.JSON
import com.mongodb.casbah.commons.Imports.DBObject
import java.util.Date
import java.text.SimpleDateFormat
import java.util.GregorianCalendar
import java.util.Calendar

object DBHandlerTest extends App {

  val dbh: DataHandlerFacade = new DataBaseHandler
  val dbh2: DataBaseHandler = new DataBaseHandler

  //dbh2.retriveUserCalls(new Date() , new Date(), "Splunk")

  val s: String = "2014/04/28 03:12:10"
  val d: Date = new Date(Date.parse(s))
  val c: GregorianCalendar = new GregorianCalendar(d.getYear(), d.getMonth(), d.getDay())
  println(d)
  println((c.getTime().getTime())/(24*60*60*1000))
  
//  dbh2.getTopTenUsers("2014/04/01 02:00:00", "2014/04/01 04:00:00", "Splunk")
  
//  dbh2.getFunctionUsage("2014/04/01 02:00:00", "2014/04/01 04:00:00", "Splunk")
  
  //dbh2.getEachUserByKey("2014/04/01 02:00:00", "2014/04/01 04:00:00", "Splunk", "71da34f94aaae4d4d4d7b9d930b275d2")
  
//  dbh2.getDistinctUsers("2014/04/01 02:00:00", "2014/04/01 04:00:00", "Splunk")
//  dbh2.getEachUserByKey("2014/04/01 02:00:00", "2014/04/01 04:00:00", "Splunk", "71da34f94aaae4d4d4d7b9d930b275d2")
  dbh2.getAllDistinctUserKeys("2014/04/01 02:00:00", "2014/04/01 04:00:00", "Splunk")

  //  var lst: List[Int] = List.empty
  //  lst = 1 :: lst
  //  lst = 2 :: lst
  //  lst = 3 :: lst
  //
  //  println(lst.contains(2))
  //
  //  val date: Date = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss").parse("23/Apr/2014:18:46:20")
  //  println(date)
  //  val gegoDate = (new GregorianCalendar(date.getYear(), date.getMonth(), date.getDay()).getTime()).getTime()/ (24 * 60 * 60 * 1000)
  //  println(gegoDate)
  //  val calDate = Calendar.getInstance()
  //  println(calDate.getTime())
  //  calDate.set(date.getYear(), date.getMonth(), date.getDay())
  //  println(calDate.getTimeInMillis()/ (24 * 60 * 60 * 1000))
  //  println(date.getTime()/ (24 * 60 * 60 * 1000))
  //  
  //  
  //  var mp: Map[String, Any] = Map.empty
  //  mp += ("k1" -> 1)
  //  mp += ("k2" -> 2)
  //  //  dbh.saveCollectionToDB(List.empty, "SplunkData")
  //
  //  //  dbh.retriveCollection("Splunk")
  //  //  dbh2.getDataSource("New Relic")
  //  var jobj: JSONObject = new JSONObject(mp)
  //  println(jobj.toString() + (new JSONObject(mp)).toString())
  //
  //  val conn = DBConnectionFactory.dbConnection()
  //  val dataCollection = conn("SensisSAPIdb")("userMetric")
  //
  //  //val dbobj: DBObject =  { "k1" :  {[ "k2" , 123]}}
  //
  //
  ////      	//      var objBuilder = new JSONObject(loggedUser.metricesMap)
  ////		//      objBuilder += new MongoDBObject("key" -> loggedUser.userKey, "metrics" -> newMap, "updatedDate" -> DateTime.now())
  ////     
  ////		//      dataCollection.save(new List[User].empty, userList)
  //     

}