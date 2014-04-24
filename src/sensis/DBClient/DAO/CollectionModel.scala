/*
 * This file contains Model classes that hold collection data.
 * They have been implemented as case classes, where class arguments will be implicitly converted 
 * to instance members.
 * 
 * Implemented by: Helini
 * Created date: 09/04/2014
 * */

package sensis.DBClient.DAO

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import scala.util.control.Exception

case class DataSource(_id: String, dsKey: String, dsName: String, updateDate: DateTime)

case class User(days: Long, userKey: String, metricesMap: Map[String, Any]) {

  var _id: String = ""
  var queryDate: DateTime = null
  var ds: DataSource = null

  def setId(id: String) = _id = id
  def setDataSource(source: DataSource) = ds = source

  override def toString = {
    var s: String = "key: " + userKey + ", queryDate:" + queryDate
    for ((key, value) <- metricesMap)
      s = s + ", " + key + ": " + value

    s
  }
}

object CallsPerUser {
    var days: Long = 0
    var ucMap: Map[String, Int] = Map.empty
  }

