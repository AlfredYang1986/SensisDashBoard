package searchQuality

import scala.util.parsing.json.JSONObject
import query.property.SensisQueryElement
import scala.util.parsing.json.JSONObject

trait SearchQualityQryTrait {

  def query(days: Int, sqe: SensisQueryElement, arr: String*): JSONObject
  def compare(b: Int, e: Int, sqe: SensisQueryElement, arr: String*): JSONObject
}