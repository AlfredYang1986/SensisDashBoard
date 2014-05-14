// @SOURCE:/Users/Dango/SensisSAPI-Dashboard/conf/routes
// @HASH:e4fecb4b56e6cad2576499e08da2e22fda99b47b
// @DATE:Wed May 07 00:52:45 EST 2014


import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString

object Routes extends Router.Routes {

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:5
private[this] lazy val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
        

// @LINE:6
private[this] lazy val controllers_Application_getSplunkUserByDate1 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("getSplunkUserByDate"))))
        

// @LINE:7
private[this] lazy val controllers_Application_getSplunkUserOverviewData2 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("getSplunkUserOverviewData"))))
        

// @LINE:8
private[this] lazy val controllers_Application_getEndPointSearch3 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("getEndPointSearch"))))
        

// @LINE:10
private[this] lazy val controllers_Assets_at4 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
        

// @LINE:11
private[this] lazy val controllers_WebJarAssets_at5 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("webjars/"),DynamicPart("file", """.+""",false))))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """getSplunkUserByDate""","""controllers.Application.getSplunkUserByDate(s:String, e:String, k:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """getSplunkUserOverviewData""","""controllers.Application.getSplunkUserOverviewData(k:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """getEndPointSearch""","""controllers.Application.getEndPointSearch(queryStr:String, queryType:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """webjars/$file<.+>""","""controllers.WebJarAssets.at(file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]] 
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:5
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil,"GET", """""", Routes.prefix + """"""))
   }
}
        

// @LINE:6
case controllers_Application_getSplunkUserByDate1(params) => {
   call(params.fromQuery[String]("s", None), params.fromQuery[String]("e", None), params.fromQuery[String]("k", None)) { (s, e, k) =>
        invokeHandler(controllers.Application.getSplunkUserByDate(s, e, k), HandlerDef(this, "controllers.Application", "getSplunkUserByDate", Seq(classOf[String], classOf[String], classOf[String]),"GET", """""", Routes.prefix + """getSplunkUserByDate"""))
   }
}
        

// @LINE:7
case controllers_Application_getSplunkUserOverviewData2(params) => {
   call(params.fromQuery[String]("k", None)) { (k) =>
        invokeHandler(controllers.Application.getSplunkUserOverviewData(k), HandlerDef(this, "controllers.Application", "getSplunkUserOverviewData", Seq(classOf[String]),"GET", """""", Routes.prefix + """getSplunkUserOverviewData"""))
   }
}
        

// @LINE:8
case controllers_Application_getEndPointSearch3(params) => {
   call(params.fromQuery[String]("queryStr", None), params.fromQuery[String]("queryType", None)) { (queryStr, queryType) =>
        invokeHandler(controllers.Application.getEndPointSearch(queryStr, queryType), HandlerDef(this, "controllers.Application", "getEndPointSearch", Seq(classOf[String], classOf[String]),"GET", """""", Routes.prefix + """getEndPointSearch"""))
   }
}
        

// @LINE:10
case controllers_Assets_at4(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        

// @LINE:11
case controllers_WebJarAssets_at5(params) => {
   call(params.fromPath[String]("file", None)) { (file) =>
        invokeHandler(controllers.WebJarAssets.at(file), HandlerDef(this, "controllers.WebJarAssets", "at", Seq(classOf[String]),"GET", """""", Routes.prefix + """webjars/$file<.+>"""))
   }
}
        
}

}
     