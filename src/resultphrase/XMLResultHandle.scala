package resultphrase

import scala.xml.XML._
import sensis.DBClient.DAO.User
import sensis.DBClient.DataHandlerFacade
import sensis.DBClient.DataBaseHandler
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar

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
		  	  		val days = new GregorianCalendar(query_date.getYear(),query_date.getMonth(),query_date.getDay())
		  	  						.getTime().getTime() / (24*60*60*1000)

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
  
//  def apply_head(result: string) = {
//    val s_beg: string = "<root>"
//    val s_end: string = "</root>"
//    var r = s_beg + result.substring(result.indexof("?>") + 2) + s_end
//    val h: scala.xml.elem = scala.xml.xml.loadstring(r)
//    (h \\ "result").map { index =>
//      (index \ "field").map { field =>
//        val k = (field \ "@k").text
//        if (k == "_raw") {
//          var raw = field.text
//          raw = raw.substring(raw.indexof('"'), raw.lastindexof('"'))
//          val method_name: string = routephrasesplunk.phrasemethodname(raw)
//          val temp: map[string, string] = routephrasesplunk.phrasearguments(raw)
//
//          var user_key = ""
//          temp.get("key") match {
//            case some(e) => user_key = temp.get("key").get
//            case none => user_key = "unknown_user"
//          }
//
//          if (userlist.s.contains(user_key))
//            userlist.s.get(user_key).get.addmethodtimes(method_name)
//          else {
//            val p = new printuser(user_key)
//            p.addmethodtimes(method_name)
//            userlist.s += (user_key -> p)
//          }
//        }
//      }
//    }
//    userlist.saveuserlist
//  }
}

object TimeUserList {
  var tu: Map[Long, UserList] = Map.empty

  def printUserList = {
    var it = tu.iterator

    while (it.hasNext) {
      val (key, value) = it.next

      //      println("time: " + key)
      value.saveUserList(key)
    }
  }
}

class UserList {

  var s: Map[String, printUser] = Map.empty

  //  def printUserList = {
  //    var it = s.iterator
  //    while (it.hasNext) {
  //      val (key, value) = it.next
  //      println("User Key: " + key)
  //      var it_in = value.callMethodMap.iterator
  //      while (it_in.hasNext) {
  //        val (me, times) = it_in.next
  //        println("    " + me + ": " + times.toString)
  //      }
  //    }
  //  }

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
