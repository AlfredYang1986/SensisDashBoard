/*
 * This factory class creates a database connection that can be used by other DB operations.
 * 
 * Implemented by: Helini
 * Created date: 07/04/2014
 */

package sensis.DBClient

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.MongoConnection
import scala.collection.immutable.List
import com.mongodb.casbah.MongoCollection

object DBConnectionFactory {

  def dbConnection(): MongoConnection = {
    MongoConnection()
  }
}