/**
 * For Database Query
 * Created By Alfred Yang
 */

package query

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query.dsl.QueryExpressionObject

trait IQueryable {
	def conn_name : String = "Alfred_Test"
	var coll_name : String = null

	private def openConnection : MongoCollection = MongoConnection()(conn_name)(coll_name)
	private def closeConnection = null
}

class ALINQ[T] {
	var w : T => Boolean = x => true
	var ls : List[T] = Nil
  
	def in(l: List[T]) : ALINQ[T] = {
		ls = l
		this
	}

	def where(f: T => Boolean) : ALINQ[T] = {
		w = f
		this
	}
	
	def select[U](cr: T => U) : List[U] = {
		for {
			i <- ls
			if (w(i))
		} yield cr(i)
	}
}

object from {
	def apply[T]() : ALINQ[T] = new ALINQ[T]
	def db() : AMongoDBLINQ = new AMongoDBLINQ
}

class AMongoDBLINQ extends IQueryable {
	var w : DBObject = null
  
	def in(l: String) : AMongoDBLINQ = {
		coll_name = l
		this
	}

	def where(args: Any* ) : AMongoDBLINQ = {
		w = new MongoDBObject
		for (arg <- args) {
			arg match {
			  case a: (String, AnyRef) => w += a
			  case a: DBObject => w ++ a
			}
		}
		this
	}
	
	def select[U](cr: (MongoDBObject) => U) : List[U] = {
	 
		// Connecting to MongoDB
		val mongoConn = MongoConnection()
		// get DB
		val mongoDB = mongoConn(conn_name)
		// get DB collection or table
		val mongoColl = mongoDB(coll_name)
		
		val ct = mongoColl.find(w).toList
		for {
			i <- ct
		} yield cr(i)
	}
}
