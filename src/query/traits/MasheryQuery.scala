package query.traits

import java.text.SimpleDateFormat
import java.util.Calendar
import scala.collection.immutable.TreeMap
import scala.util.parsing.json.JSONObject
import com.mongodb.DBObject
import com.mongodb.casbah.Imports.mongoQueryStatements
import com.mongodb.casbah.commons.MongoDBObject
import query.BaseTimeSpan
import query.from
import query.property.SensisQueryElement
import cache.MasheryDatabaseName
import query.IQueryable

object MasheryQuery extends QueryTraits {
  def isQueryable(property: String): Boolean = false

  def query(b: Int, e: Int, p: SensisQueryElement, r: String*): JSONObject = {
    new JSONObject(queryBase(b, e, p, r.toArray))
  }

  def queryCount(b: Int, e: Int, p: SensisQueryElement, r: String*): JSONObject = {
    var countMap: TreeMap[String, Int] = TreeMap.empty

    (queryBase(b, e, p, r.toArray)).foreach(x => {
      countMap += (x._1 -> (x._2).size)
    })
    new JSONObject(countMap)
  }

  def queryTops(t: Int, b: Int, e: Int, p: SensisQueryElement, r: String*): JSONObject = ???

  def queryTopsWithQueryable(t: Int, b: Int, e: Int, p: SensisQueryElement, r: String*): IQueryable[SensisQueryElement] = ???

  def queryWithQueryable(b: Int, e: Int, p: SensisQueryElement, r: String*): IQueryable[SensisQueryElement] = ???

  def queryBase(start: Int, end: Int, conditions: SensisQueryElement, req: Array[String]): Map[String, List[SensisQueryElement]] = {

    def queryConditions: DBObject = {
      val dbObjectBuilder = MongoDBObject.newBuilder
      for (it <- conditions)
        dbObjectBuilder += (it.name -> it.get)

      dbObjectBuilder.result
    }

    def resultConditions(fl: Array[String]): MongoDBObject => SensisQueryElement = {
      x =>
        {
          val re: SensisQueryElement = new SensisQueryElement
          for (it <- fl)
            re.insertProperty(it, x.getAsOrElse(it, ""))
          re
        }
    }

    /* Date conversion process for matching Mashery String dates */
    def getStringDate(day: Int): String = {
      val cal = Calendar.getInstance()
      cal.setTime(BaseTimeSpan.base)
      cal.add(Calendar.DATE, day)
      new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())
    }

    /* Conditions for results */
    var fl: Array[String] = null
    if (req.head.equals("*"))
      fl = Array("display_name", "first_name", "last_name", "username", "email",
        "area_status", "postal_code", "country_code", "created", "updated")
    else
      fl = req.toList.toArray

    /* Data retrieval */
    var userMap: TreeMap[String, List[SensisQueryElement]] = TreeMap.empty
    for (i <- start to end) {
      val tmpData = from db () in MasheryDatabaseName.mashery_data where (("created" $regex getStringDate(i)), queryConditions) select resultConditions(fl)
      userMap += (i.toString -> tmpData.toList)

    }
    userMap
  }

	def queryTopsWithQueryable(t : Int, b : Int, e : Int, p : SensisQueryElement, r : String*) : IQueryable[SensisQueryElement] = ???
	def queryWithQueryable(b : Int, e : Int, p : SensisQueryElement, r : String*) : IQueryable[SensisQueryElement] = ???

}
