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

//abstract class GenericCollection {
//
//  def _id: Int
//  def customID:Int
//}

case class DataSource(_id: Int, customId: Int, dsKey: String, dsName: String, updateDate: DateTime) {

}

case class UserMetric(_id: Int, customId: Int, ds: DataSource,
  updateDate: DateTime,
  activeUsers: List[Any],
  inactiveUsers: List[Any],
  successCalls: Map[String, Any],
  errorCalls: Map[String, Any]) {

}

case class PerformanceMetric(_id: Int, customId: Int, ds: DataSource, updateDate: DateTime)

