
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
object main extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,Html,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(title: String)(content: Html):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.32*/("""

<!DOCTYPE html>
<html>
<head>
    <title>"""),_display_(Seq[Any](/*6.13*/title)),format.raw/*6.18*/("""</title>
    <link rel='shortcut icon' type='image/png' href='"""),_display_(Seq[Any](/*7.55*/routes/*7.61*/.Assets.at("images/favicon.png"))),format.raw/*7.93*/("""'>
  
    <script type='text/javascript' src='"""),_display_(Seq[Any](/*9.42*/routes/*9.48*/.WebJarAssets.at(WebJarAssets.locate("jquery.min.js")))),format.raw/*9.102*/("""'></script>
   
   
</head>
<body>
    
    <div class="container">
        """),_display_(Seq[Any](/*16.10*/content)),format.raw/*16.17*/("""
    </div>
</body>
</html>
"""))}
    }
    
    def render(title:String,content:Html): play.api.templates.HtmlFormat.Appendable = apply(title)(content)
    
    def f:((String) => (Html) => play.api.templates.HtmlFormat.Appendable) = (title) => (content) => apply(title)(content)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Tue May 06 17:03:19 EST 2014
                    SOURCE: /Users/Dango/SensisSAPI-Dashboard/app/views/main.scala.html
                    HASH: b7c424dde0723f319fe67ce0b8fe061c815babb4
                    MATRIX: 560->1|684->31|763->75|789->80|887->143|901->149|954->181|1036->228|1050->234|1126->288|1239->365|1268->372
                    LINES: 19->1|22->1|27->6|27->6|28->7|28->7|28->7|30->9|30->9|30->9|37->16|37->16
                    -- GENERATED --
                */
            