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

case class User(userKey: String, metricesMap: Map[String, Any]) {

  var _id: String = ""
  var updateDate: DateTime = DateTime.now()
  var ds: DataSource = null

  def setId(id: String) = _id = id
  def setDataSource(source: DataSource) = ds = source

//    def listingsInHeadingInState: Int = metricesMap.getOrElse("index/listingsInHeadingInState", 10)
    
  //  def listingsInLetterPartition: Int = metricesMap("index/listingsInLetterPartition")
  //  def letterPartitions: Int = metricesMap("index/letterPartitions")
  //  def searchByName: Int = metricesMap("searchByName")
  //  def localitiesInState: Int = metricesMap("index/localitiesInState")
  //  def getAllByFamilyIdAndState: Int = metricesMap("index/getAllByFamilyIdAndState")
  //  def getByListingId: Int = metricesMap("getByListingId")
  //  def listingsInHeadingInLocality: Int = metricesMap("index/listingsInHeadingInLocality")
  //  def categoriesInRegionInState: Int = metricesMap("index/categoriesInRegionInState")
  //  def categoriesInLocality: Int = metricesMap("index/categoriesInLocality")
  //  def serviceArea: Int = metricesMap("serviceArea")
  //  def search: Int = metricesMap("search")

  override def toString = {
    var s: String = "key: " + userKey + ", updateDate:" + updateDate
    for ((key, value) <- metricesMap)
      s = s + ", " + key + ": " + value
      
    s
  }
  
}

