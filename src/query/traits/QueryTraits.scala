/**
 * For Front-End query
 * Created By Alfred Yang
 * 16th May, 2014
 */

package query.traits

import scala.util.parsing.json.JSONObject
import query.property.SensisQueryElement
import query.IQueryable

trait QueryTraits {

	/**
	 * Is the query traits can handle this message
	 */
	def isQueryable(property : String) : Boolean
	
	/**
	 * query
	 * @b : start date, days to 19/01/1986
	 * @e : end   date, days to 19/01/1986
	 * @p : properties that want to query
	 */
	def query(b : Int, e : Int, p : SensisQueryElement, r : String*) : JSONObject
	def queryTops(t : Int, b : Int, e : Int, p : SensisQueryElement, r : String*) : JSONObject 
	def queryTopsWithQueryable(t : Int, b : Int, e : Int, p : SensisQueryElement, r : String*) : IQueryable[SensisQueryElement]
}

object QueryTraitsFactory {
	def apply(name : String) : QueryTraits = name match {
	  case "Mashery" => MasheryQuery 
	  case "SplunkData" => SplunkQuery
	  case "Relic" => RelicQuery
	  case "SplunkQuery" => SplunkQLQuery
	  case _ => ???
	}
}
