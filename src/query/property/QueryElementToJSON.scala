package query.property

import scala.util.parsing.json.JSONObject
import scala.util.parsing.json.JSONArray

object QueryElementToJSON {
	def apply(xs : List[SensisQueryElement]) : JSONObject = {
		var reVal : List[JSONObject] = Nil
		for (it <- xs) reVal = reVal :+ (it.toJSONMap)
		
		new JSONObject(Map("items" -> new JSONArray(reVal)))
	}
}