// @SOURCE:/Users/Dango/SensisSAPI-Dashboard/conf/routes
// @HASH:e4fecb4b56e6cad2576499e08da2e22fda99b47b
// @DATE:Wed May 07 00:52:45 EST 2014

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString


// @LINE:11
// @LINE:10
// @LINE:8
// @LINE:7
// @LINE:6
// @LINE:5
package controllers {

// @LINE:11
class ReverseWebJarAssets {
    

// @LINE:11
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "webjars/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          

// @LINE:10
class ReverseAssets {
    

// @LINE:10
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          

// @LINE:8
// @LINE:7
// @LINE:6
// @LINE:5
class ReverseApplication {
    

// @LINE:7
def getSplunkUserOverviewData(k:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "getSplunkUserOverviewData" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("k", k)))))
}
                                                

// @LINE:6
def getSplunkUserByDate(s:String, e:String, k:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "getSplunkUserByDate" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("s", s)), Some(implicitly[QueryStringBindable[String]].unbind("e", e)), Some(implicitly[QueryStringBindable[String]].unbind("k", k)))))
}
                                                

// @LINE:5
def index(): Call = {
   Call("GET", _prefix)
}
                                                

// @LINE:8
def getEndPointSearch(queryStr:String, queryType:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "getEndPointSearch" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("queryStr", queryStr)), Some(implicitly[QueryStringBindable[String]].unbind("queryType", queryType)))))
}
                                                
    
}
                          
}
                  


// @LINE:11
// @LINE:10
// @LINE:8
// @LINE:7
// @LINE:6
// @LINE:5
package controllers.javascript {

// @LINE:11
class ReverseWebJarAssets {
    

// @LINE:11
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.WebJarAssets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "webjars/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              

// @LINE:10
class ReverseAssets {
    

// @LINE:10
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              

// @LINE:8
// @LINE:7
// @LINE:6
// @LINE:5
class ReverseApplication {
    

// @LINE:7
def getSplunkUserOverviewData : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.getSplunkUserOverviewData",
   """
      function(k) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "getSplunkUserOverviewData" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("k", k)])})
      }
   """
)
                        

// @LINE:6
def getSplunkUserByDate : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.getSplunkUserByDate",
   """
      function(s,e,k) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "getSplunkUserByDate" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("s", s), (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("e", e), (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("k", k)])})
      }
   """
)
                        

// @LINE:5
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        

// @LINE:8
def getEndPointSearch : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.getEndPointSearch",
   """
      function(queryStr,queryType) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "getEndPointSearch" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("queryStr", queryStr), (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("queryType", queryType)])})
      }
   """
)
                        
    
}
              
}
        


// @LINE:11
// @LINE:10
// @LINE:8
// @LINE:7
// @LINE:6
// @LINE:5
package controllers.ref {


// @LINE:11
class ReverseWebJarAssets {
    

// @LINE:11
def at(file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.WebJarAssets.at(file), HandlerDef(this, "controllers.WebJarAssets", "at", Seq(classOf[String]), "GET", """""", _prefix + """webjars/$file<.+>""")
)
                      
    
}
                          

// @LINE:10
class ReverseAssets {
    

// @LINE:10
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          

// @LINE:8
// @LINE:7
// @LINE:6
// @LINE:5
class ReverseApplication {
    

// @LINE:7
def getSplunkUserOverviewData(k:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.getSplunkUserOverviewData(k), HandlerDef(this, "controllers.Application", "getSplunkUserOverviewData", Seq(classOf[String]), "GET", """""", _prefix + """getSplunkUserOverviewData""")
)
                      

// @LINE:6
def getSplunkUserByDate(s:String, e:String, k:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.getSplunkUserByDate(s, e, k), HandlerDef(this, "controllers.Application", "getSplunkUserByDate", Seq(classOf[String], classOf[String], classOf[String]), "GET", """""", _prefix + """getSplunkUserByDate""")
)
                      

// @LINE:5
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq(), "GET", """""", _prefix + """""")
)
                      

// @LINE:8
def getEndPointSearch(queryStr:String, queryType:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.getEndPointSearch(queryStr, queryType), HandlerDef(this, "controllers.Application", "getEndPointSearch", Seq(classOf[String], classOf[String]), "GET", """""", _prefix + """getEndPointSearch""")
)
                      
    
}
                          
}
        
    