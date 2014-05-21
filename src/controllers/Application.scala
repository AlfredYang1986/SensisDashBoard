//package controllers
//
//import play.api.mvc.{Action, Controller}
//import query._
//import query.helper._
//import play.api.libs.json._
//import scala.util.parsing.json.JSONObject
//import org.joda.time.Days
//import org.joda.time.DateTime
//import java.text.SimpleDateFormat
//import query.property.SensisQueryElement
//import query.property.QueryElementToJSON
//import com.mongodb.casbah.Imports._
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.Date
//import scala.collection.immutable.TreeMap
//import login.UserLogin
//import views.html.admin
//import views.html.addUser
//
//object Application extends Controller {
//  
//		 def index = Action {
//		    
//		    val users = from db() in "splunkdata" select 
//						SplunkHelper.querySplunkDBOToQueryObject("key")
//						
//			val f = users.distinctBy(x => x.getProperty[String]("key")).toList
//			
//			val d = getUserCountForDateRange("2014-5-01","2014-5-03").toMap
//			
//		    Ok(views.html.index("Hello Play Framework",f,d))
//		}
//		 
//		def datePickForMashery(s:String, e:String) = Action {
//			
//		    val sb = new StringBuilder
//		    
//			val d = getUserCountForDateRange(s,e).toMap
//			
//			for(u <- d){
//			  sb.append(u._1 + ",")
//			}
//		    sb.append(":")
//		    for(i <- d){
//		      sb.append(i._2 + ",")
//		    }
//		    Ok(sb.toString)
//		   
//		}
//		 
//		
//		
//		
//		/*def admin = Action { 
//		  Ok(views.html.admin("Test"))
//		}*/
//		def admin = Action { implicit request =>
//        if (!request.session.get("username").isEmpty) {
//            //Redirect("/login")
//          Ok(views.html.admin("admin"))
//        } else {
//            Ok(views.html.login("login"))
//        }
//    }
//		
//		/*def admin = Authenticated {
//            Ok(views.html.admin("admin"))
//        }*/		
//		
//		def addUser = Action {
//		  Ok(views.html.addUser("Test"))
//		}
//  
//      	def summary = Action {		    
//			Ok(views.html.summary("Test"))
//		} 
//            	
//        def quality = Action {		    
//			Ok(views.html.quality("Test"))
//		} 
//            	
//        def report = Action {		    
//			Ok(views.html.report("Test"))
//		} 
//            	
//        def insights = Action {		    
//			Ok(views.html.insights("Test"))
//		} 
//            
//        def login = Action {		    
//			Ok(views.html.login("Test"))
//		} 
//        
//        def DeleteUser = Action {
//         Ok(views.html.DeleteUser("Test"))
//		} 
//        
//        def auth(name:String,pass:String) = Action{request =>
//          Ok("Welcome!").withSession(
//        	request.session + ("username" -> "name"))
//          
//         if(authorization(name,pass))
//         {
//          Ok(authorization(name,pass).toString()).withSession(
//        	request.session + ("username" -> "name")) 
//         }
//         else{
//          Ok(authorization(name,pass).toString()).withSession(
//        	request.session + ("username" -> "name"))
//         }
//          }
//        
//          def add(name1:String,pass1:String) = Action{
//         if(addAdmin(name1,pass1))
//          Ok(addAdmin(name1,pass1).toString())         
//          Ok(addAdmin(name1,pass1).toString())
//          }
//          
//           def del(nameu:String) = Action{
//         if(deleteAdmin(nameu))
//          Ok(deleteAdmin(nameu).toString())         
//          Ok(deleteAdmin(nameu).toString())
//          }
//          
//        def loginAsInitialAdmin(username : String, password : String) : Boolean = 
//		// 1. the origin password is 8888
//		// 2. username must be Pete, Andrew, Karl, Willy
//		if ("8888" == password && List("Pete", "Andrew", "Karl", "Willy").contains(username)) {
//			_data_connection.getCollection("user_name") += MongoDBObject("username" -> username, "password" -> "8888")
//			
//			Redirect(routes.Application.admin)
//			true
//		} else false
//	
//	def authorization(username : String, password : String) : Boolean = 
//		if (from db() in "user_name" where ("username" -> username, "password" -> password) contains) {
//		  
//		  true
//		  
//		}
//		  
//		else loginAsInitialAdmin(username, password)
//
//		
//		
//	def addAdmin(username : String, password : String) : Boolean = 
//		if (from db() in "user_name" where ("username" -> username) contains) false
//		else {
//		  _data_connection.getCollection("user_name") += MongoDBObject("username" -> username, "password" -> password)
//		  true
//		}
//        
//	
//	def deleteAdmin(username : String) : Boolean = 
//		(from db() in "user_name" where ("username" -> username) select (x => x)).fistOrDefault match {
//		  case Some(e) => _data_connection.getCollection("user_name") -= e; true
//		  case none => false
//		}
//	
//	def changePassword(username : String, password : String) : Boolean =
//		(from db() in "user_name" where ("username" -> username) select (x => x)).fistOrDefault match {
//		  case Some(e) => _data_connection.getCollection("user_name").update(e, ("password" $eq password)); true
//		  case none => false
//		}
//        
//	    def getSplunkUserByDate(s:String, e:String, k:String) = Action {   
//	      
//	    	val queryName : List[String] = List("search","getByListingId", "serviceArea", "index/listingsInHeadingInLocality", "singleSearch", "report/appearance", "report/viewDetails", "index/topCategoriesInLocality", "index/localitiesInState")
//	    
//	    	val start = Days.daysBetween(new DateTime(BaseTimeSpan.base),
//	    				new DateTime(new SimpleDateFormat("yyyy-MM-dd").parse(s))).getDays()
//	        
//	    	val end = Days.daysBetween(new DateTime(BaseTimeSpan.base),
//	    				new DateTime(new SimpleDateFormat("yyyy-MM-dd").parse(e))).getDays()
//	        
//	    	val query_time_name = from db() in "splunkdata" where 
//	    				  		  SplunkHelper.queryByUserKeyBetweenTimespanDB(k, start, end) select
//						  	      SplunkHelper.querySplunkDBOToQueryObject(queryName)
//						  	  
//			val user = query_time_name.aggregate(SplunkHelper.AggregateByProperty("key"), SplunkHelper.AggregateSumSplunkData(queryName))
//	    
//	        val query_001 = user.top(1)
//	        
//		    var m1 : Map[String, Any] = Map.empty
//		    
//		    query_001.toList.map(x=>x.args.map{ y =>
//		    			     if (y.name.contains("/")) m1 += (y.name.substring(y.name.indexOf('/') + 1) ->y.get)
//		    			     else m1 +=(y.name -> y.get)    
//	        }) 
//	        
//	        Ok(new JSONObject(m1).toString())
//	    }
//  
//	  def getSplunkUserOverviewData(k: String) = Action{
//	    
//		  val userKey = k;  
//		  
//		  val queryName : List[String] = List("search","getByListingId", "serviceArea", "index/listingsInHeadingInLocality", "singleSearch", "report/appearance", "report/viewDetails", "index/topCategoriesInLocality", "index/localitiesInState")
//		  
//		  val query = from db() in "splunkdata" where 
//					  SplunkHelper.queryByUserKeyDB(userKey) select
//					  SplunkHelper.querySplunkDBOToQueryObject(queryName)
//					  
//		  val user = query.aggregate(SplunkHelper.AggregateByProperty("key"), SplunkHelper.AggregateSumSplunkData(queryName))
//		  
//		  val query_001 = user.top(1)
//		  
//		  var m1 : Map[String, Any] = Map.empty
//		  
//		  query_001.toList.map(x=>x.args.map{ y =>
//		  				   if (y.name.contains("/")) m1 += (y.name.substring(y.name.indexOf('/') + 1) ->y.get)
//		  				   else m1 +=(y.name -> y.get)    
//		  }) 
//		  
//	        Ok(new JSONObject(m1).toString())
//	  }	
//  
//  
//	  def getEndPointSearch(s: String, e: String) = Action {
//	      
////	      val sb = new StringBuilder
////	      for(u <- getQueryOccurances(s,e)){
////	        sb.append(u.getProperty("query").toString + "/" + u.getProperty("location") + ": " + u.getProperty("occurances") + ",")
////	      }	    
//	      Ok(QueryElementToJSON(getQueryOccurances(s,e)).toString())
//	  }
//	  
//	  def getTopTen(s: String, e: String) = Action {
//          Ok(QueryElementToJSON(getTopTenQueries(s,e)).toString())
//	  }
//	  
//	  def getUserCountForDateRange(startDate: String, endDate:String) = { 
//	    
//	     var userCountMap:TreeMap[String,Int] = TreeMap.empty
//	     
//	     val end: Date = new SimpleDateFormat("yyyy-MM-dd").parse(endDate) 
//	     
//	     val rangeCounter = Calendar.getInstance()
//	     
//	     rangeCounter.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(startDate))    
//	        
//	     while ((rangeCounter.getTime()).getTime() <= end.getTime()){
//	           val rangeCounterStr = (new SimpleDateFormat("yyyy-MM-dd")).format(rangeCounter.getTime())
//	           val queryData = from db() in "masherydata" where ("created" $regex rangeCounterStr) select 
//	           MasheryHelper.queryMasheryDBOToQueryObject("email", "username")
//	           userCountMap += (rangeCounterStr -> queryData.count)            
//	           rangeCounter.add(Calendar.DATE, 1)
//	    }
//	    userCountMap
//	  }
//	 
//	 def getQueryOccuranceBase(start: String, end: String): IQueryable[SensisQueryElement] = {
//      val queryData = start match {
//      case e: String => {
//        if (end != null)
//          from db () in "splunk_query_data" where (SplunkHelper.queryBetweenTimespanDB(getIntDays(start), getIntDays(end))) select SplunkHelper.getSplunkQueriesToObject("query", "location", "occurances")
//        else
//          from db () in "splunk_query_data" where ("days" $eq getIntDays(start)) select SplunkHelper.getSplunkQueriesToObject("query", "location", "occurances")
//      }
//      case none => throw new Exception
//    }
//    val distinctQueries = queryData.aggregate(
//      SplunkHelper.AggregateByProperty("query"),
//      SplunkHelper.AggregateSumQueryData("occurances")).orderby(x => { x.getProperty[String]("query") })
//
//    distinctQueries
//  }
//
//  /**
//   * Return the distinct queries as a list. 
//   */
//  def getQueryOccurances(start: String, end: String): List[SensisQueryElement] = {
//    (getQueryOccuranceBase(start, end)).toList
//  }
//
//  /**
//   * Return the top 10 distinct queries, as a list. 
//   */
//    def getTopTenQueries(start: String, end: String): List[SensisQueryElement] = {
//
//    val topTenQueries = (getQueryOccuranceBase(start, end)).orderbyDecsending(x => { x.getProperty[Int]("occurances") }).top(10)
//    
//    topTenQueries.toList
//    
//    }
//	 
//	 def getIntDays(dateStr: String): Int = {
//	   
//	    var givenDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr)
//	   
//	    givenDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(givenDate))
//	    
//	    val daysInRange: Int = Days.daysBetween(new DateTime(BaseTimeSpan.base), new DateTime(givenDate)).getDays()
//	    
//	    daysInRange
//	 }
//	 	 
//}