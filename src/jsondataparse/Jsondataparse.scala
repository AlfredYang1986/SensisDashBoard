/**
 * JSON parse
 * Created by Alfred Yang
 * 2nd April, 2014
 */

package jsondataparse

import scala.util.parsing.json._
import scala.collection.immutable.HashMap
import errorreport.Error_PhraseJosn

abstract class Jsondataparse {
	def parse(input: String, keys: List[String]) : Map[String, Any]
}

object parser extends Jsondataparse {
	def parse(input: String, keys: List[String]) = {
		val p = JSON.parseFull(input)
		p match {
			case Some(e) => {
				val mp = e.asInstanceOf[Map[String, Any]]
				var result : Map[String, Any] = Map.empty
				for (key <- keys) {
					if (mp.contains(key))
						result = result + (key -> mp.get(key).get)
				}
				result
			}
		    case None => throw Error_PhraseJosn
		}
	}
}