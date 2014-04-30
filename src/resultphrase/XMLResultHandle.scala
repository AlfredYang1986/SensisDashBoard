package resultphrase

import scala.xml.XML._
import sensis.DBClient.DAO.User
import sensis.DBClient.DataHandlerFacade
import sensis.DBClient.DataBaseHandler
import java.text.SimpleDateFormat
import java.util.Date
import org.joda.time.Days
import org.joda.time.DateTime

object XMLResultHandle extends ResultHandle {

  def apply(result: String) = {
		val s_beg : String = "<root>"
		val s_end : String = "</root>"
		var r = s_beg + result.substring(result.indexOf("?>") + 2) + s_end
		val h: scala.xml.Elem = scala.xml.XML.loadString(r)
		(h \\ "result").map { index =>
		  	(index \ "field").map { field =>
		  	  	val k = (field \ "@k").text
		  	  	if (k == "_raw") {
		  	  		var raw = field.text

		  	  		var str_date : String = raw.substring(raw.indexOf('[') + 1, raw.indexOf(']'))
		  	  		str_date = str_date.substring(0, str_date.indexOf(' '))
		  	  		val query_date : java.util.Date = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss").parse(str_date)
		  	  		val base : java.util.Date = new Date(0, 0, 1)

		  	  		val days = Days.daysBetween(new DateTime(base), new DateTime(query_date)).getDays()

		  	  		var ul : UserList = null
		  	  		if (TimeUserList.tu.contains(days))  ul = TimeUserList.tu.get(days).get
		  	  		else ul = new UserList
		  	  		
		  	  		raw = raw.substring(raw.indexOf('"'), raw.lastIndexOf('"'))
		  	  		val method_name: String = RoutePhraseSplunk.phraseMethodName(raw)
		  	  		val temp: Map[String, String] = RoutePhraseSplunk.phraseArguments(raw)
		  	  	
		  	  		var user_key = ""
		  	  		temp.get("key") match{
		  	  		  case Some(e) => user_key = temp.get("key").get
		  	  		  case none => user_key = "Unknown_User"
		  	  		}
		  	  		
		  	  		if (ul.s.contains(user_key)) ul.s.get(user_key).get.addMethodTimes(method_name)
		  	  		else {
		  	  			val p = new printUser(user_key)
		  	  			p.addMethodTimes(method_name)
		  	  			ul.s += (user_key -> p)
		  	  		}

		  	  		TimeUserList.tu += (days -> ul)
		  	  	}
		  	}
		}
		TimeUserList.printUserList
	}

}

object TimeUserList {
//  var tu: Map[Long, UserList] = Map.empty
  var tu: Map[Int, UserList] = Map.empty

  def printUserList = {
    var it = tu.iterator

    while (it.hasNext) {
      val (key, value) = it.next
      
      value.saveUserList(key)
//    	  println(key)
//      value.printUserList
    }
  }
}

class UserList {

  var s: Map[String, printUser] = Map.empty

    def printUserList = {
      var it = s.iterator
      while (it.hasNext) {
        val (key, value) = it.next
        println("User Key: " + key)
        var it_in = value.callMethodMap.iterator
        while (it_in.hasNext) {
          val (me, times) = it_in.next
          println("    " + me + ": " + times.toString)
        }
      }
    }

  def saveUserList(days: Long) = {
    // List to hold the User instances
    var userList: List[User] = List.empty
    // Obtaining iterator for traversing data Map
    var it = s.iterator

    while (it.hasNext) {
      val (key, value) = it.next
      // Extracting the metrics map.
      var innerMetricMap = value.callMethodMap
      // Creating new user object of each user data collection.
      var loggedUser: User = User(days, key, innerMetricMap)
      userList = loggedUser :: userList
    }
    
    // Insert data in to DB
    val dataHandlerFacade: DataHandlerFacade = new DataBaseHandler
    dataHandlerFacade.saveCollectionToDB(userList, "splunkdata")
  }
}

case class printUser(key: String) {
  var callMethodMap: Map[String, Int] = Map.empty // Alfred Demo (MethodName -> Times)

  def addMethodTimes(name: String) = {

    if (callMethodMap.contains(name)) {
      val i = 1 + callMethodMap.get(name).get
      //      callMethodMap -= name
      callMethodMap += (name -> i)
    } else
      callMethodMap += (name -> 1)
  }
}
