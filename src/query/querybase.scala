/**
 * For Database Query
 * Created By Alfred Yang
 */

package query

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.query.dsl.QueryExpressionObject

object _data_connection {
	def conn_name : String = "SensisSAPIdb"
//	def conn_name : String = "Alfred_Test"

	val _conn = MongoConnection()
	var _conntion : Map[String, MongoCollection] = Map.empty
	
	def getCollection(coll_name : String) : MongoCollection = {
		if (!_conntion.contains(coll_name)) _conntion += (coll_name -> _conn(conn_name)(coll_name))
		
		_conntion.get(coll_name).get
	}
}

trait IDatabaseContext {
	var coll_name : String = null

	protected def openConnection : MongoCollection = 
	  	_data_connection._conn(_data_connection.conn_name)(coll_name)
	protected def closeConnection = null
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
	
	def select[U](cr: (T) => U) : IQueryable[U] = {
		var nc = new Linq_List[U]
		for (i <- ls) {
			if (w(i)) nc = (nc :+ cr(i)).asInstanceOf[Linq_List[U]]
		}
		nc
	}
	
	def contains : Boolean = {
		for (i <- ls) {
			if (w(i)) true
		}
		false
	}
}

object from {
	def apply[T]() : ALINQ[T] = new ALINQ[T]
	def db() : AMongoDBLINQ = new AMongoDBLINQ
}

class AMongoDBLINQ extends IDatabaseContext {
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
			  case a: DBObject => w = w ++ a
			}
		}
		this
	}
	
	def select[U](cr: (MongoDBObject) => U) : IQueryable[U] = {
	 
		val mongoColl = openConnection
		val ct = mongoColl.find(w).toList
		var nc = new Linq_List[U]
		for (i <- ct) {
			nc = (nc :+ cr(i)).asInstanceOf[Linq_List[U]]
		}
		nc
	}

	def contains : Boolean = {
		val mongoColl = openConnection
		!mongoColl.findOne.isEmpty
	}
}
