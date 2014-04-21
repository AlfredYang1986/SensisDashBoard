package resultphrase

import scala.xml.XML._
import sensis.DBClient.DAO.User
import sensis.DBClient.DataHandlerFacade
import sensis.DBClient.DataBaseHandler

object XMLResultHandle extends ResultHandle {
  def apply(result: String) = {
    val s_beg: String = "<root>"
    val s_end: String = "</root>"
    var r = s_beg + result.substring(result.indexOf("?>") + 2) + s_end
    val h: scala.xml.Elem = scala.xml.XML.loadString(r)
    (h \\ "result").map { index =>
      (index \ "field").map { field =>
        val k = (field \ "@k").text
        if (k == "_raw") {
          //		  	  		println(field.text)
          var raw = field.text
          raw = raw.substring(raw.indexOf('"'), raw.lastIndexOf('"'))
          val method_name: String = RoutePhraseSplunk.phraseMethodName(raw)
          val temp: Map[String, String] = RoutePhraseSplunk.phraseArguments(raw)

          var user_key = ""
          temp.get("key") match {
            case Some(e) => user_key = temp.get("key").get
            case none => user_key = "Unknown_User"
          }

          if (UserList.s.contains(user_key))
            UserList.s.get(user_key).get.addMethodTimes(method_name)
          else {
            val p = new printUser(user_key)
            p.addMethodTimes(method_name)
            UserList.s += (user_key -> p)
          }
        }
      }
    }
    UserList.saveUserList
  }
}

object UserList {

  var s: Map[String, printUser] = Map.empty

  def saveUserList = {
    // List to hold the User instances
    var userList: List[User] = List.empty
    // Obtaining iterator for traversing data Map
    var it = s.iterator

    while (it.hasNext) {      
      val (key, value) = it.next
      // Extracting the metrics map.
      var innerMetricMap = value.callMethodMap
      // Creating new user object of each user data collection.
      var loggedUser: User = User(key, innerMetricMap)
      userList = loggedUser :: userList      
    }
    // Insert data in to DB
    val dataHandlerFacade:DataHandlerFacade =new DataBaseHandler
    dataHandlerFacade.saveCollectionToDB(userList, "SplunkData")
  }
}

case class printUser(key: String) {
  var callMethodMap: Map[String, Int] = Map.empty // Alfred Demo (MethodName -> Times)
  def addMethodTimes(name: String) = {
    if (callMethodMap.contains(name)) {
      val i = 1 + callMethodMap.get(name).get
      callMethodMap -= name
      callMethodMap += (name -> i)
    } else callMethodMap += (name -> 1)
  }
}
