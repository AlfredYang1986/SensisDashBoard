
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import views.html._
/**/
object index extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template3[String,List[String],Map[String, Int],play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(message: String, splunkUsers: List[String], masheryData: Map[String,Int]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.76*/("""


"""),_display_(Seq[Any](/*4.2*/main("Sensis Project")/*4.24*/ {_display_(Seq[Any](format.raw/*4.26*/("""

    <link href='"""),_display_(Seq[Any](/*6.18*/routes/*6.24*/.Assets.at("bootstrap/css/bootstrap.css"))),format.raw/*6.65*/("""' rel="stylesheet">

    <!-- Loading Flat UI -->
    <link href='"""),_display_(Seq[Any](/*9.18*/routes/*9.24*/.Assets.at("css/flat-ui.css"))),format.raw/*9.53*/("""' rel="stylesheet">
    <link href='"""),_display_(Seq[Any](/*10.18*/routes/*10.24*/.Assets.at("css/demo.css"))),format.raw/*10.50*/("""' rel="stylesheet">  
    
    

  
    
    <!-- Loading JS -->
    <script src='"""),_display_(Seq[Any](/*17.19*/routes/*17.25*/.Assets.at("js/jquery-1.8.3.min.js"))),format.raw/*17.61*/("""'></script>
    <script src='"""),_display_(Seq[Any](/*18.19*/routes/*18.25*/.Assets.at("js/jquery-ui-1.10.3.custom.min.js"))),format.raw/*18.72*/("""'></script>
    <script src='"""),_display_(Seq[Any](/*19.19*/routes/*19.25*/.Assets.at("js/jquery.ui.touch-punch.min.js"))),format.raw/*19.70*/("""'></script>
    <script src='"""),_display_(Seq[Any](/*20.19*/routes/*20.25*/.Assets.at("js/bootstrap.min.js"))),format.raw/*20.58*/("""'></script>
    <script src='"""),_display_(Seq[Any](/*21.19*/routes/*21.25*/.Assets.at("js/bootstrap-select.js"))),format.raw/*21.61*/("""'></script>
    <script src='"""),_display_(Seq[Any](/*22.19*/routes/*22.25*/.Assets.at("js/bootstrap-switch.js"))),format.raw/*22.61*/("""'></script>
    <script src='"""),_display_(Seq[Any](/*23.19*/routes/*23.25*/.Assets.at("js/flatui-checkbox.js"))),format.raw/*23.60*/("""'></script>
    <script src='"""),_display_(Seq[Any](/*24.19*/routes/*24.25*/.Assets.at("js/flatui-radio.js"))),format.raw/*24.57*/("""'></script>
    <script src='"""),_display_(Seq[Any](/*25.19*/routes/*25.25*/.Assets.at("js/jquery.tagsinput.js"))),format.raw/*25.61*/("""'></script>
    <script src='"""),_display_(Seq[Any](/*26.19*/routes/*26.25*/.Assets.at("js/jquery.placeholder.js"))),format.raw/*26.63*/("""'></script>
    <script src='"""),_display_(Seq[Any](/*27.19*/routes/*27.25*/.Assets.at("js/jquery.stacktable.js"))),format.raw/*27.62*/("""'></script>
    <script src='"""),_display_(Seq[Any](/*28.19*/routes/*28.25*/.Assets.at("http://vjs.zencdn.net/4.3/video.js"))),format.raw/*28.73*/("""'></script>
    <script src='"""),_display_(Seq[Any](/*29.19*/routes/*29.25*/.Assets.at("js/application.js"))),format.raw/*29.56*/("""'></script>
    <script type='text/javascript' src='"""),_display_(Seq[Any](/*30.42*/routes/*30.48*/.WebJarAssets.at(WebJarAssets.locate("jquery.min.js")))),format.raw/*30.102*/("""'></script>

  
   
    <!-- Dango css/js -->
    <style type="text/css">
    .radius"""),format.raw/*36.12*/("""{"""),format.raw/*36.13*/("""
            border-top-left-radius:10px;
            border-top-right-radius:10px;
            """),format.raw/*39.13*/("""}"""),format.raw/*39.14*/("""
            
            
    .rowrow """),format.raw/*42.13*/("""{"""),format.raw/*42.14*/("""
             width:100%;
            """),format.raw/*44.13*/("""}"""),format.raw/*44.14*/("""
    .table-responsive """),format.raw/*45.23*/("""{"""),format.raw/*45.24*/("""
             overflow:scroll;
             height:450px;
             width:360px;
   
            """),format.raw/*50.13*/("""}"""),format.raw/*50.14*/("""       
    
    </style>
    
    
    <script type="text/javascript">
    
            
    </script>
    
    <link href="http://cdn.kendostatic.com/2014.1.318/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.1.318/styles/kendo.default.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.1.318/styles/kendo.dataviz.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.1.318/styles/kendo.dataviz.default.min.css" rel="stylesheet" />


  
    
    
    <script src="http://cdn.kendostatic.com/2014.1.318/js/kendo.all.min.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

  <body>

    <!-- nav********************************** -->     
<div class="row demo-row rowrow">
        <div class="col-xs-12">
          <nav class="navbar navbar-inverse  navbar-fixed-top navbar-embossed" role="navigation">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-01">
                <span class="sr-only">Toggle navigation</span>
              </button>
            </div>
            <div class="collapse navbar-collapse" id="navbar-collapse-01">
              <ul class="nav navbar-nav navbar-left">           
                <li><a href="#fakelink">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Sensis SAPI<span class="navbar-unread">1</span></a></li>
                 <li><a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                <li><a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                <li><a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                   <li><a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                <li><a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                <li><a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                <li><a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                <li><a>&nbsp;&nbsp;&nbsp;</a></li>
                <li><a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">Messages <b class="caret"></b></a>
                  <span class="dropdown-arrow"></span>
                  <ul class="dropdown-menu">
                    <li><a href="#">Action</a></li>
                    <li><a href="#">Another action</a></li>
                    <li><a href="#">Something else here</a></li>
                    <li class="divider"></li>
                    <li><a href="#">Separated link</a></li>
                  </ul>
                </li>
                <li><a href="#fakelink">About Us</a></li>
              
                <li class="navbar-form navbar-right" action="#" role="search">
                    
      <div class="form-group">
   
        <div class="input-group">
          <input class="form-control" id="navbarInput-01" type="search" placeholder="Search">
          <span class="input-group-btn">
            <button type="submit" class="btn"><span class="fui-search"></span></button>
          </span>            
        </div>
       
      </div> 
    
    </li>
            </div>
               </ul>
            </div><!-- /.navbar-collapse -->
          </nav><!-- /navbar -->
        </div>
</div>
<!-- nav********************************** -->  



<!-- sidebar********************************** -->  

<div id="le" style="float:left;margin-top:32px;background-color:#34495e;width:15%;">
<ul class="nav nav-pills nav-stacked ">
  <li id="click" onclick="click()"><a href="#">&nbsp;<span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;&nbsp;Home</span></a></li>
  <li><a href="#" >&nbsp;<span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;&nbsp;Profile</a></li>
  <li><a href="#">&nbsp;<span class="glyphicon glyphicon-comment"></span>&nbsp;&nbsp;&nbsp;Messages</a></li>
</ul>
<hr>
</div>

<!-- sidebar********************************** -->  

    
<!-- div********************************** put any thing u want-->  
<div id="ri" style="float:left;margin-left:30px;margin-top:55px;width:81%">
       
  <div id="page-wrapper">
            
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading" style="color:#16a085">
                             Mashery Data
                            
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div id="linechart" style="margin-left:13px;margin-right:13px"></div>
                          
                           
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                    
                    <div class="panel panel-default">
                        <div class="panel-heading" style="color:#16a085">
                            Search Query/Location
                            <div class="pull-right">
                            <input type = "radio" name = "queryType" id = "byLocation" value = "location" checked = "checked" />
                 		    <label for = "byLocation">ByLocation</label>
          
          

					        <input type = "radio" name = "queryType" id = "byQuery" value = "query" />
					        <label for = "byQuery">ByQuery</label>
					        <input type="text" id="queryString" class="ddd"/> 
                      
                            <div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" id="endPointSearch">
                                        GO
                                    </button>
                            </div>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                         
                             <div id="barchart" style="margin-left:13px;margin-right:13px"></div>
                        </div>
                        </div>
                        
                        
                    <div class="panel panel-default">
                        <div class="panel-heading" style="color:#16a085">
                             User Usage  
                            <div class="pull-right">
                            	   <input type="text" id="startDate" placeholder="StartAt">
                                   <input type="text" id="finishDate" placeholder="EndAt">
                                <div class="btn-group">                                   
                                   <button type="button" class="btn btn-default btn-dropdown-toggle" data-toggle="dropdown" id="pickUser">
                                        GO
                                    </button>
                                  
                                </div>
                            </div>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-4">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-hover table-striped">
                                                 <tbody >
                                                 """),_display_(Seq[Any](/*218.51*/splunkUsers/*218.62*/.map/*218.66*/ { u =>_display_(Seq[Any](format.raw/*218.73*/("""
                                                 <tr>
                                                  <td><a onclick='showSplunkOverviewData(""""),_display_(Seq[Any](/*220.92*/u)),format.raw/*220.93*/("""")';>"""),_display_(Seq[Any](/*220.99*/u)),format.raw/*220.100*/("""</a></td>
                                                  </tr>
                                                  """)))})),format.raw/*222.52*/("""
                                                  </tbody>
                                        </table>
                                    </div>
                                    <!-- /.table-responsive -->
                                </div>
                                <!-- /.col-lg-4 (nested) -->
                                <div class="col-lg-8">
                                 <div id="chart"></div>
                                </div>
                                <!-- /.col-lg-8 (nested) -->
                           
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                    </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-8 -->
             
                 
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->      

    
    
    
    <!-- footer********************************** -->  
<footer>
      <div class="container">
        <div class="row">
          <div class="col-xs-7">
            <h3 class="footer-title">Subscribe</h3>
            <p>Do you like this freebie? Want to get more stuff like this?<br/>
              Subscribe to designmodo news and updates to stay tuned on great designs.<br/>
              Go to: <a href="http://designmodo.com/flat-free" target="_blank">designmodo.com/flat-free</a>
            </p>

         

          
          </div> <!-- /col-xs-7 -->

          <div class="col-xs-5">
            <div style="background-color:#1abc9c;color:white">
              <h3 class="footer-title">&nbsp;&nbsp;Get Flat UI Pro</h3>
              <ul>
                <li>Tons of Basic and Custom UI Elements</li>
                <li>A Lot of Useful Samples</li>
                <li>More Vector Icons and Glyphs</li>
              </ul>
              &nbsp;&nbsp;Go to: <a href="http://designmodo.com/flat" target="_blank">designmodo.com/flat</a>
            </div>
          </div>
        </div>
      </div>
</footer>
<!-- footer********************************** -->  
    </body>
    
    <script type="text/javascript">    
   
        
        
        
        
        
              function showUsage(search,getByListingId,serviceArea,appearance,categoriesInlocality,listingsInheadingInlocality,localitiesInState,signleSearch,userviewDetails,topCategoriesInLocality)"""),format.raw/*297.199*/("""{"""),format.raw/*297.200*/("""
         $("#chart").kendoChart("""),format.raw/*298.33*/("""{"""),format.raw/*298.34*/("""
                  title: """),format.raw/*299.26*/("""{"""),format.raw/*299.27*/("""
                      position: "top",
                      text: "Usage Distribution"
                  """),format.raw/*302.19*/("""}"""),format.raw/*302.20*/(""",
                  legend: """),format.raw/*303.27*/("""{"""),format.raw/*303.28*/("""
                      visible: false
                  """),format.raw/*305.19*/("""}"""),format.raw/*305.20*/(""",
                  chartArea: """),format.raw/*306.30*/("""{"""),format.raw/*306.31*/("""
                      background: ""
                  """),format.raw/*308.19*/("""}"""),format.raw/*308.20*/(""",
                  seriesDefaults: """),format.raw/*309.35*/("""{"""),format.raw/*309.36*/("""
                      labels: """),format.raw/*310.31*/("""{"""),format.raw/*310.32*/("""
                          visible: true,
                          background: "transparent",
                          template: "#= category #: #= value#"
                      """),format.raw/*314.23*/("""}"""),format.raw/*314.24*/("""
                  """),format.raw/*315.19*/("""}"""),format.raw/*315.20*/(""",
                  series: ["""),format.raw/*316.28*/("""{"""),format.raw/*316.29*/("""
                      type: "pie",
                      startAngle: 180,
                      data: ["""),format.raw/*319.30*/("""{"""),format.raw/*319.31*/("""
                           category: "search"+"-"+search,
                          value: toDecimal2(search*100.0/(search+getByListingId+serviceArea+appearance+categoriesInlocality+listingsInheadingInlocality+localitiesInState+signleSearch+userviewDetails+topCategoriesInLocality)),
                          color: "#e74c3c"
                      """),format.raw/*323.23*/("""}"""),format.raw/*323.24*/(""","""),format.raw/*323.25*/("""{"""),format.raw/*323.26*/("""
                          category: "getByListingId"+"-"+getByListingId,
                          value: toDecimal2(getByListingId*100.0/(search+getByListingId+serviceArea+appearance+categoriesInlocality+listingsInheadingInlocality+localitiesInState+signleSearch+userviewDetails+topCategoriesInLocality)),
                          color: "#f1c40f"
                      """),format.raw/*327.23*/("""}"""),format.raw/*327.24*/(""","""),format.raw/*327.25*/("""{"""),format.raw/*327.26*/("""
                          category: "serviceArea"+"-"+serviceArea,
                          value: toDecimal2(serviceArea*100.0/(search+getByListingId+serviceArea+appearance+categoriesInlocality+listingsInheadingInlocality+localitiesInState+signleSearch+userviewDetails+topCategoriesInLocality)),
                          color: "#3498db"
                      """),format.raw/*331.23*/("""}"""),format.raw/*331.24*/(""","""),format.raw/*331.25*/("""{"""),format.raw/*331.26*/("""
                          category: "appearance"+"-"+appearance,
                          value: toDecimal2(appearance*100.0/(search+getByListingId+serviceArea+appearance+categoriesInlocality+listingsInheadingInlocality+localitiesInState+signleSearch+userviewDetails+topCategoriesInLocality)),
                          color: "#34495e"
                      """),format.raw/*335.23*/("""}"""),format.raw/*335.24*/(""","""),format.raw/*335.25*/("""{"""),format.raw/*335.26*/("""
                          category: "categoriesInlocality"+"-"+categoriesInlocality,
                          value: toDecimal2(categoriesInlocality*100.0/(search+getByListingId+serviceArea+appearance+categoriesInlocality+listingsInheadingInlocality+localitiesInState+signleSearch+userviewDetails+topCategoriesInLocality)),
                          color: "#2ecc71"
                      """),format.raw/*339.23*/("""}"""),format.raw/*339.24*/(""","""),format.raw/*339.25*/("""{"""),format.raw/*339.26*/("""
                          category: "listingsInheadingInlocality"+"-"+listingsInheadingInlocality,
                          value: toDecimal2(listingsInheadingInlocality*100.0/(search+getByListingId+serviceArea+appearance+categoriesInlocality+listingsInheadingInlocality+localitiesInState+signleSearch+userviewDetails+topCategoriesInLocality)),
                          color: "#1abc9c"
                      """),format.raw/*343.23*/("""}"""),format.raw/*343.24*/(""","""),format.raw/*343.25*/("""{"""),format.raw/*343.26*/("""
                          category: "localitiesInState"+"-"+localitiesInState,
                          value: toDecimal2(localitiesInState*100.0/(search+getByListingId+serviceArea+appearance+categoriesInlocality+listingsInheadingInlocality+localitiesInState+signleSearch+userviewDetails+topCategoriesInLocality)),
                          color: "#9b59b6"
                      """),format.raw/*347.23*/("""}"""),format.raw/*347.24*/(""","""),format.raw/*347.25*/("""{"""),format.raw/*347.26*/("""
                          category: "signleSearch"+"-"+signleSearch,
                          value: toDecimal2(signleSearch*100.0/(search+getByListingId+serviceArea+appearance+categoriesInlocality+listingsInheadingInlocality+localitiesInState+signleSearch+userviewDetails+topCategoriesInLocality)),
                          color: "#ecf0f1"
                      """),format.raw/*351.23*/("""}"""),format.raw/*351.24*/(""","""),format.raw/*351.25*/("""{"""),format.raw/*351.26*/("""
                          category: "userviewDetails"+"-"+userviewDetails,
                          value: toDecimal2(userviewDetails*100.0/(search+getByListingId+serviceArea+appearance+categoriesInlocality+listingsInheadingInlocality+localitiesInState+signleSearch+userviewDetails+topCategoriesInLocality)),
                          color: "#95a5a6"
                      """),format.raw/*355.23*/("""}"""),format.raw/*355.24*/(""","""),format.raw/*355.25*/("""{"""),format.raw/*355.26*/("""
                          category: "topCategoriesInLocality"+"-"+topCategoriesInLocality,
                          value: toDecimal2(topCategoriesInLocality*100.0/(search+getByListingId+serviceArea+appearance+categoriesInlocality+listingsInheadingInlocality+localitiesInState+signleSearch+userviewDetails+topCategoriesInLocality)),
                          color: "#e67e22"
                      """),format.raw/*359.23*/("""}"""),format.raw/*359.24*/("""]                      
                  """),format.raw/*360.19*/("""}"""),format.raw/*360.20*/("""],
                  tooltip: """),format.raw/*361.28*/("""{"""),format.raw/*361.29*/("""
                      visible: true,
                      format: """"),format.raw/*363.32*/("""{"""),format.raw/*363.33*/("""0"""),format.raw/*363.34*/("""}"""),format.raw/*363.35*/("""%"
                  """),format.raw/*364.19*/("""}"""),format.raw/*364.20*/("""
              """),format.raw/*365.15*/("""}"""),format.raw/*365.16*/(""");
          """),format.raw/*366.11*/("""}"""),format.raw/*366.12*/("""
                     
	   $(document).ready(function () """),format.raw/*368.35*/("""{"""),format.raw/*368.36*/(""" 
	   userCallingInfo()
       """),format.raw/*370.8*/("""}"""),format.raw/*370.9*/("""); 

	   function showSplunkOverviewData(key)"""),format.raw/*372.41*/("""{"""),format.raw/*372.42*/("""
		   $("#startDate").val("StartAt");
		   $("#finishDate").val("EndAt");
		   document.cookie = key;
		   $.ajax("""),format.raw/*376.13*/("""{"""),format.raw/*376.14*/("""
	            type: "GET",
	            url: "/getSplunkUserOverviewData",
	            datatype: "JSON",
	            data: """),format.raw/*380.20*/("""{"""),format.raw/*380.21*/("""k: key  """),format.raw/*380.29*/("""}"""),format.raw/*380.30*/("""
	            """),format.raw/*381.14*/("""}"""),format.raw/*381.15*/(""").done(function(userUsage)"""),format.raw/*381.41*/("""{"""),format.raw/*381.42*/("""
	            var obj = JSON.parse(userUsage);
               
	            // showUsage(obj.search,obj.getByListingId,obj.serviceArea,obj.appearance,obj.categoriesInlocality,obj.listingsInheadingInlocality,obj.localitiesInState,obj.signleSearch,obj.userviewDetails,obj.topCategoriesInLocality);
                showUsage(obj.search,obj.getByListingId,obj.serviceArea,obj.appearance,obj.topCategoriesInLocality, obj.listingsInHeadingInLocality,obj.localitiesInState,obj.singleSearch,obj.viewDetails,obj.topCategoriesInLocality);
	            """),format.raw/*386.14*/("""}"""),format.raw/*386.15*/(""");
	   """),format.raw/*387.5*/("""}"""),format.raw/*387.6*/("""
	   
	   
       $('#pickUser').click(function() """),format.raw/*390.40*/("""{"""),format.raw/*390.41*/("""
       var key = document.cookie;
       var sDate = $("#startDate").val();
       var eDate = $("#finishDate").val();
           $.ajax("""),format.raw/*394.19*/("""{"""),format.raw/*394.20*/("""
            type: "GET",
            url: "/getSplunkUserByDate",
            datatype: "JSON",
            data: """),format.raw/*398.19*/("""{"""),format.raw/*398.20*/("""s: sDate, e: eDate, k: key"""),format.raw/*398.46*/("""}"""),format.raw/*398.47*/("""
            """),format.raw/*399.13*/("""}"""),format.raw/*399.14*/(""").done(function(userUsage)"""),format.raw/*399.40*/("""{"""),format.raw/*399.41*/("""
            	var obj = JSON.parse(userUsage);
    
	            //showUsage(obj.search,obj.getByListingId,obj.serviceArea,obj.appearance,obj.categoriesInlocality,obj.listingsInheadingInlocality,obj.localitiesInState,obj.signleSearch,obj.userviewDetails,obj.topCategoriesInLocality);
                showUsage(obj.search,obj.getByListingId,obj.serviceArea,obj.appearance,obj.topCategoriesInLocality, obj.listingsInHeadingInLocality,obj.localitiesInState,obj.singleSearch,obj.viewDetails,obj.topCategoriesInLocality);
            """),format.raw/*404.13*/("""}"""),format.raw/*404.14*/(""");         
       """),format.raw/*405.8*/("""}"""),format.raw/*405.9*/(""");

       $('#endPointSearch').click(function() """),format.raw/*407.46*/("""{"""),format.raw/*407.47*/("""          
    	   var queryType = $("input:radio[name='queryType']:checked").val();
    	   var queryString = $("#queryString").val();
               $.ajax("""),format.raw/*410.23*/("""{"""),format.raw/*410.24*/("""
                type: "GET",
                url: "/getEndPointSearch",
                datatype: "JSON",
                data: """),format.raw/*414.23*/("""{"""),format.raw/*414.24*/("""queryStr: queryString, queryType: queryType"""),format.raw/*414.67*/("""}"""),format.raw/*414.68*/("""
                """),format.raw/*415.17*/("""}"""),format.raw/*415.18*/(""").done(function(message)"""),format.raw/*415.42*/("""{"""),format.raw/*415.43*/("""         	
                	endPointData(queryString,message)
                """),format.raw/*417.17*/("""}"""),format.raw/*417.18*/(""");         
         """),format.raw/*418.10*/("""}"""),format.raw/*418.11*/(""");
         
         function endPointData(key,value) """),format.raw/*420.43*/("""{"""),format.raw/*420.44*/("""
         var t = value + 300;
         var keyT = key;
         var valueT = value;
            $("#barchart").kendoChart("""),format.raw/*424.39*/("""{"""),format.raw/*424.40*/("""
                title: """),format.raw/*425.24*/("""{"""),format.raw/*425.25*/("""
                    text: ""
                """),format.raw/*427.17*/("""}"""),format.raw/*427.18*/(""",
                legend: """),format.raw/*428.25*/("""{"""),format.raw/*428.26*/("""
                    visible: false
                """),format.raw/*430.17*/("""}"""),format.raw/*430.18*/(""",
                seriesDefaults: """),format.raw/*431.33*/("""{"""),format.raw/*431.34*/("""
                    type: "bar"
                """),format.raw/*433.17*/("""}"""),format.raw/*433.18*/(""",
                series: ["""),format.raw/*434.26*/("""{"""),format.raw/*434.27*/("""
                    name: "Total Visits",
                    data: [valueT]
                """),format.raw/*437.17*/("""}"""),format.raw/*437.18*/("""],
                valueAxis: """),format.raw/*438.28*/("""{"""),format.raw/*438.29*/("""
                    max: 7000,
                    line: """),format.raw/*440.27*/("""{"""),format.raw/*440.28*/("""
                        visible: false
                    """),format.raw/*442.21*/("""}"""),format.raw/*442.22*/(""",
                    minorGridLines: """),format.raw/*443.37*/("""{"""),format.raw/*443.38*/("""
                        visible: true
                    """),format.raw/*445.21*/("""}"""),format.raw/*445.22*/("""
                """),format.raw/*446.17*/("""}"""),format.raw/*446.18*/(""",
                categoryAxis: """),format.raw/*447.31*/("""{"""),format.raw/*447.32*/("""
                    categories: [keyT],
                    majorGridLines: """),format.raw/*449.37*/("""{"""),format.raw/*449.38*/("""
                        visible: false
                    """),format.raw/*451.21*/("""}"""),format.raw/*451.22*/("""
                """),format.raw/*452.17*/("""}"""),format.raw/*452.18*/(""",
                tooltip: """),format.raw/*453.26*/("""{"""),format.raw/*453.27*/("""
                    visible: true,
                    template: "#= series.name #: #= value #"
                """),format.raw/*456.17*/("""}"""),format.raw/*456.18*/("""
            """),format.raw/*457.13*/("""}"""),format.raw/*457.14*/(""");
        """),format.raw/*458.9*/("""}"""),format.raw/*458.10*/("""

       function userCallingInfo() """),format.raw/*460.35*/("""{"""),format.raw/*460.36*/("""
           $("#linechart").kendoChart("""),format.raw/*461.39*/("""{"""),format.raw/*461.40*/("""
               title: """),format.raw/*462.23*/("""{"""),format.raw/*462.24*/("""
                   text: "Information of new user registration in last 6 years"
               """),format.raw/*464.16*/("""}"""),format.raw/*464.17*/(""",
               legend: """),format.raw/*465.24*/("""{"""),format.raw/*465.25*/("""
                   position: "bottom"
               """),format.raw/*467.16*/("""}"""),format.raw/*467.17*/(""",
               chartArea: """),format.raw/*468.27*/("""{"""),format.raw/*468.28*/("""
                   background: ""
               """),format.raw/*470.16*/("""}"""),format.raw/*470.17*/(""",
               seriesDefaults: """),format.raw/*471.32*/("""{"""),format.raw/*471.33*/("""
                   type: "line",
                   style: "smooth"
               """),format.raw/*474.16*/("""}"""),format.raw/*474.17*/(""",
               series: ["""),format.raw/*475.25*/("""{"""),format.raw/*475.26*/("""
                   name: "Number of new User",
                   data: ["""),_display_(Seq[Any](/*477.28*/for( (element,0) <- masheryData.zipWithIndex ) yield /*477.74*/ {_display_(Seq[Any](_display_(Seq[Any](/*477.77*/element/*477.84*/._2))))})),format.raw/*477.88*/(""", """),_display_(Seq[Any](/*477.91*/for( (element,1) <- masheryData.zipWithIndex ) yield /*477.137*/ {_display_(Seq[Any](_display_(Seq[Any](/*477.140*/element/*477.147*/._2))))})),format.raw/*477.151*/(""", """),_display_(Seq[Any](/*477.154*/for( (element,2) <- masheryData.zipWithIndex ) yield /*477.200*/ {_display_(Seq[Any](_display_(Seq[Any](/*477.203*/element/*477.210*/._2))))})),format.raw/*477.214*/(""", """),_display_(Seq[Any](/*477.217*/for( (element,3) <- masheryData.zipWithIndex ) yield /*477.263*/ {_display_(Seq[Any](_display_(Seq[Any](/*477.266*/element/*477.273*/._2))))})),format.raw/*477.277*/(""", """),_display_(Seq[Any](/*477.280*/for( (element,4) <- masheryData.zipWithIndex ) yield /*477.326*/ {_display_(Seq[Any](_display_(Seq[Any](/*477.329*/element/*477.336*/._2))))})),format.raw/*477.340*/(""", """),_display_(Seq[Any](/*477.343*/for( (element,5) <- masheryData.zipWithIndex ) yield /*477.389*/ {_display_(Seq[Any](_display_(Seq[Any](/*477.392*/element/*477.399*/._2))))})),format.raw/*477.403*/("""]
               """),format.raw/*478.16*/("""}"""),format.raw/*478.17*/("""],
               valueAxis: """),format.raw/*479.27*/("""{"""),format.raw/*479.28*/("""
                   labels: """),format.raw/*480.28*/("""{"""),format.raw/*480.29*/("""
                       format: """"),format.raw/*481.33*/("""{"""),format.raw/*481.34*/("""0"""),format.raw/*481.35*/("""}"""),format.raw/*481.36*/(""""
                   """),format.raw/*482.20*/("""}"""),format.raw/*482.21*/(""",
                   line: """),format.raw/*483.26*/("""{"""),format.raw/*483.27*/("""
                       visible: false
                   """),format.raw/*485.20*/("""}"""),format.raw/*485.21*/(""",
                   axisCrossingValue: -10
               """),format.raw/*487.16*/("""}"""),format.raw/*487.17*/(""",
               categoryAxis: """),format.raw/*488.30*/("""{"""),format.raw/*488.31*/("""
                   categories: ["""),_display_(Seq[Any](/*489.34*/for( (element,0) <- masheryData.zipWithIndex ) yield /*489.80*/ {_display_(Seq[Any](_display_(Seq[Any](/*489.83*/element/*489.90*/._1))))})),format.raw/*489.94*/(""", """),_display_(Seq[Any](/*489.97*/for( (element,1) <- masheryData.zipWithIndex ) yield /*489.143*/ {_display_(Seq[Any](_display_(Seq[Any](/*489.146*/element/*489.153*/._1))))})),format.raw/*489.157*/(""", """),_display_(Seq[Any](/*489.160*/for( (element,2) <- masheryData.zipWithIndex ) yield /*489.206*/ {_display_(Seq[Any](_display_(Seq[Any](/*489.209*/element/*489.216*/._1))))})),format.raw/*489.220*/(""", """),_display_(Seq[Any](/*489.223*/for( (element,3) <- masheryData.zipWithIndex ) yield /*489.269*/ {_display_(Seq[Any](_display_(Seq[Any](/*489.272*/element/*489.279*/._1))))})),format.raw/*489.283*/(""", """),_display_(Seq[Any](/*489.286*/for( (element,4) <- masheryData.zipWithIndex ) yield /*489.332*/ {_display_(Seq[Any](_display_(Seq[Any](/*489.335*/element/*489.342*/._1))))})),format.raw/*489.346*/(""", """),_display_(Seq[Any](/*489.349*/for( (element,5) <- masheryData.zipWithIndex ) yield /*489.395*/ {_display_(Seq[Any](_display_(Seq[Any](/*489.398*/element/*489.405*/._1))))})),format.raw/*489.409*/("""],
                   majorGridLines: """),format.raw/*490.36*/("""{"""),format.raw/*490.37*/("""
                       visible: false
                   """),format.raw/*492.20*/("""}"""),format.raw/*492.21*/("""
               """),format.raw/*493.16*/("""}"""),format.raw/*493.17*/(""",
               tooltip: """),format.raw/*494.25*/("""{"""),format.raw/*494.26*/("""
                   visible: true,
                   format: """"),format.raw/*496.29*/("""{"""),format.raw/*496.30*/("""0"""),format.raw/*496.31*/("""}"""),format.raw/*496.32*/("""",
                   template: "#= series.name #: #= value #"
               """),format.raw/*498.16*/("""}"""),format.raw/*498.17*/("""
           """),format.raw/*499.12*/("""}"""),format.raw/*499.13*/(""");
       """),format.raw/*500.8*/("""}"""),format.raw/*500.9*/("""
       
       
       function toDecimal2(x) """),format.raw/*503.31*/("""{"""),format.raw/*503.32*/("""    
            var f = parseFloat(x);    
            if (isNaN(f)) """),format.raw/*505.27*/("""{"""),format.raw/*505.28*/("""    
                return false;    
            """),format.raw/*507.13*/("""}"""),format.raw/*507.14*/("""    
            var f = Math.round(x*100)/100;    
            var s = f.toString();    
            var rs = s.indexOf('.');    
            if (rs < 0) """),format.raw/*511.25*/("""{"""),format.raw/*511.26*/("""    
                rs = s.length;    
                s += '.';    
            """),format.raw/*514.13*/("""}"""),format.raw/*514.14*/("""    
            while (s.length <= rs + 2) """),format.raw/*515.40*/("""{"""),format.raw/*515.41*/("""    
                s += '0';    
            """),format.raw/*517.13*/("""}"""),format.raw/*517.14*/("""    
            return s;    
        """),format.raw/*519.9*/("""}"""),format.raw/*519.10*/("""    
        
        
        $(function()"""),format.raw/*522.21*/("""{"""),format.raw/*522.22*/("""
       	$( "#startDate" ).datepicker();
       	$( "#finishDate" ).datepicker();
     
            if($("#le").height() > $("#ri").height())"""),format.raw/*526.54*/("""{"""),format.raw/*526.55*/("""
            $("#ri").css("height",$("#le").height()) 
            """),format.raw/*528.13*/("""}"""),format.raw/*528.14*/("""else"""),format.raw/*528.18*/("""{"""),format.raw/*528.19*/("""
            $("#le").css("height",$("#ri").height()) 
            """),format.raw/*530.13*/("""}"""),format.raw/*530.14*/("""
            """),format.raw/*531.13*/("""}"""),format.raw/*531.14*/(""");
                 
  </script>   
 """)))})),format.raw/*534.3*/("""
"""))}
    }
    
    def render(message:String,splunkUsers:List[String],masheryData:Map[String, Int]): play.api.templates.HtmlFormat.Appendable = apply(message,splunkUsers,masheryData)
    
    def f:((String,List[String],Map[String, Int]) => play.api.templates.HtmlFormat.Appendable) = (message,splunkUsers,masheryData) => apply(message,splunkUsers,masheryData)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Wed May 07 09:50:06 EST 2014
                    SOURCE: /Users/Dango/SensisSAPI-Dashboard/app/views/index.scala.html
                    HASH: 889b413d199335effcca34bfc957ec8a6c71c4a8
                    MATRIX: 586->1|754->75|792->79|822->101|861->103|915->122|929->128|991->169|1093->236|1107->242|1157->271|1230->308|1245->314|1293->340|1412->423|1427->429|1485->465|1551->495|1566->501|1635->548|1701->578|1716->584|1783->629|1849->659|1864->665|1919->698|1985->728|2000->734|2058->770|2124->800|2139->806|2197->842|2263->872|2278->878|2335->913|2401->943|2416->949|2470->981|2536->1011|2551->1017|2609->1053|2675->1083|2690->1089|2750->1127|2816->1157|2831->1163|2890->1200|2956->1230|2971->1236|3041->1284|3107->1314|3122->1320|3175->1351|3264->1404|3279->1410|3356->1464|3469->1549|3498->1550|3622->1646|3651->1647|3718->1686|3747->1687|3813->1725|3842->1726|3893->1749|3922->1750|4050->1850|4079->1851|12063->9798|12084->9809|12098->9813|12144->9820|12327->9966|12351->9967|12394->9973|12419->9974|12569->10091|15148->12640|15179->12641|15241->12674|15271->12675|15326->12701|15356->12702|15492->12809|15522->12810|15579->12838|15609->12839|15694->12895|15724->12896|15784->12927|15814->12928|15899->12984|15929->12985|15994->13021|16024->13022|16084->13053|16114->13054|16323->13234|16353->13235|16401->13254|16431->13255|16489->13284|16519->13285|16652->13389|16682->13390|17061->13740|17091->13741|17121->13742|17151->13743|17553->14116|17583->14117|17613->14118|17643->14119|18036->14483|18066->14484|18096->14485|18126->14486|18516->14847|18546->14848|18576->14849|18606->14850|19026->15241|19056->15242|19086->15243|19116->15244|19557->15656|19587->15657|19617->15658|19647->15659|20058->16041|20088->16042|20118->16043|20148->16044|20544->16411|20574->16412|20604->16413|20634->16414|21039->16790|21069->16791|21099->16792|21129->16793|21558->17193|21588->17194|21659->17236|21689->17237|21748->17267|21778->17268|21876->17337|21906->17338|21936->17339|21966->17340|22016->17361|22046->17362|22090->17377|22120->17378|22162->17391|22192->17392|22278->17449|22308->17450|22367->17481|22396->17482|22470->17527|22500->17528|22643->17642|22673->17643|22827->17768|22857->17769|22894->17777|22924->17778|22967->17792|22997->17793|23052->17819|23082->17820|23653->18362|23683->18363|23718->18370|23747->18371|23826->18421|23856->18422|24023->18560|24053->18561|24197->18676|24227->18677|24282->18703|24312->18704|24354->18717|24384->18718|24439->18744|24469->18745|25027->19274|25057->19275|25104->19294|25133->19295|25211->19344|25241->19345|25428->19503|25458->19504|25616->19633|25646->19634|25718->19677|25748->19678|25794->19695|25824->19696|25877->19720|25907->19721|26014->19799|26044->19800|26094->19821|26124->19822|26208->19877|26238->19878|26390->20001|26420->20002|26473->20026|26503->20027|26578->20073|26608->20074|26663->20100|26693->20101|26774->20153|26804->20154|26867->20188|26897->20189|26975->20238|27005->20239|27061->20266|27091->20267|27214->20361|27244->20362|27303->20392|27333->20393|27420->20451|27450->20452|27539->20512|27569->20513|27636->20551|27666->20552|27754->20611|27784->20612|27830->20629|27860->20630|27921->20662|27951->20663|28057->20740|28087->20741|28176->20801|28206->20802|28252->20819|28282->20820|28338->20847|28368->20848|28510->20961|28540->20962|28582->20975|28612->20976|28651->20987|28681->20988|28746->21024|28776->21025|28844->21064|28874->21065|28926->21088|28956->21089|29081->21185|29111->21186|29165->21211|29195->21212|29278->21266|29308->21267|29365->21295|29395->21296|29474->21346|29504->21347|29566->21380|29596->21381|29709->21465|29739->21466|29794->21492|29824->21493|29936->21568|29999->21614|30049->21617|30066->21624|30097->21628|30137->21631|30201->21677|30252->21680|30270->21687|30302->21691|30343->21694|30407->21740|30458->21743|30476->21750|30508->21754|30549->21757|30613->21803|30664->21806|30682->21813|30714->21817|30755->21820|30819->21866|30870->21869|30888->21876|30920->21880|30961->21883|31025->21929|31076->21932|31094->21939|31126->21943|31172->21960|31202->21961|31260->21990|31290->21991|31347->22019|31377->22020|31439->22053|31469->22054|31499->22055|31529->22056|31579->22077|31609->22078|31665->22105|31695->22106|31782->22164|31812->22165|31900->22224|31930->22225|31990->22256|32020->22257|32091->22291|32154->22337|32204->22340|32221->22347|32252->22351|32292->22354|32356->22400|32407->22403|32425->22410|32457->22414|32498->22417|32562->22463|32613->22466|32631->22473|32663->22477|32704->22480|32768->22526|32819->22529|32837->22536|32869->22540|32910->22543|32974->22589|33025->22592|33043->22599|33075->22603|33116->22606|33180->22652|33231->22655|33249->22662|33281->22666|33348->22704|33378->22705|33465->22763|33495->22764|33540->22780|33570->22781|33625->22807|33655->22808|33747->22871|33777->22872|33807->22873|33837->22874|33944->22952|33974->22953|34015->22965|34045->22966|34083->22976|34112->22977|34188->23024|34218->23025|34317->23095|34347->23096|34427->23147|34457->23148|34641->23303|34671->23304|34782->23386|34812->23387|34885->23431|34915->23432|34991->23479|35021->23480|35088->23519|35118->23520|35190->23563|35220->23564|35390->23705|35420->23706|35516->23773|35546->23774|35579->23778|35609->23779|35705->23846|35735->23847|35777->23860|35807->23861|35877->23899
                    LINES: 19->1|22->1|25->4|25->4|25->4|27->6|27->6|27->6|30->9|30->9|30->9|31->10|31->10|31->10|38->17|38->17|38->17|39->18|39->18|39->18|40->19|40->19|40->19|41->20|41->20|41->20|42->21|42->21|42->21|43->22|43->22|43->22|44->23|44->23|44->23|45->24|45->24|45->24|46->25|46->25|46->25|47->26|47->26|47->26|48->27|48->27|48->27|49->28|49->28|49->28|50->29|50->29|50->29|51->30|51->30|51->30|57->36|57->36|60->39|60->39|63->42|63->42|65->44|65->44|66->45|66->45|71->50|71->50|239->218|239->218|239->218|239->218|241->220|241->220|241->220|241->220|243->222|318->297|318->297|319->298|319->298|320->299|320->299|323->302|323->302|324->303|324->303|326->305|326->305|327->306|327->306|329->308|329->308|330->309|330->309|331->310|331->310|335->314|335->314|336->315|336->315|337->316|337->316|340->319|340->319|344->323|344->323|344->323|344->323|348->327|348->327|348->327|348->327|352->331|352->331|352->331|352->331|356->335|356->335|356->335|356->335|360->339|360->339|360->339|360->339|364->343|364->343|364->343|364->343|368->347|368->347|368->347|368->347|372->351|372->351|372->351|372->351|376->355|376->355|376->355|376->355|380->359|380->359|381->360|381->360|382->361|382->361|384->363|384->363|384->363|384->363|385->364|385->364|386->365|386->365|387->366|387->366|389->368|389->368|391->370|391->370|393->372|393->372|397->376|397->376|401->380|401->380|401->380|401->380|402->381|402->381|402->381|402->381|407->386|407->386|408->387|408->387|411->390|411->390|415->394|415->394|419->398|419->398|419->398|419->398|420->399|420->399|420->399|420->399|425->404|425->404|426->405|426->405|428->407|428->407|431->410|431->410|435->414|435->414|435->414|435->414|436->415|436->415|436->415|436->415|438->417|438->417|439->418|439->418|441->420|441->420|445->424|445->424|446->425|446->425|448->427|448->427|449->428|449->428|451->430|451->430|452->431|452->431|454->433|454->433|455->434|455->434|458->437|458->437|459->438|459->438|461->440|461->440|463->442|463->442|464->443|464->443|466->445|466->445|467->446|467->446|468->447|468->447|470->449|470->449|472->451|472->451|473->452|473->452|474->453|474->453|477->456|477->456|478->457|478->457|479->458|479->458|481->460|481->460|482->461|482->461|483->462|483->462|485->464|485->464|486->465|486->465|488->467|488->467|489->468|489->468|491->470|491->470|492->471|492->471|495->474|495->474|496->475|496->475|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|498->477|499->478|499->478|500->479|500->479|501->480|501->480|502->481|502->481|502->481|502->481|503->482|503->482|504->483|504->483|506->485|506->485|508->487|508->487|509->488|509->488|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|510->489|511->490|511->490|513->492|513->492|514->493|514->493|515->494|515->494|517->496|517->496|517->496|517->496|519->498|519->498|520->499|520->499|521->500|521->500|524->503|524->503|526->505|526->505|528->507|528->507|532->511|532->511|535->514|535->514|536->515|536->515|538->517|538->517|540->519|540->519|543->522|543->522|547->526|547->526|549->528|549->528|549->528|549->528|551->530|551->530|552->531|552->531|555->534
                    -- GENERATED --
                */
            