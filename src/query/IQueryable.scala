/**
 * make unique interface for all the ALINQ query
 * Created By Alfred Yang
 */

package query

trait IEnumerable {
	type TResult
	type TContainer
	
	def AsQueryable : IQueryable[TResult]
	def Case[TResult] : IEnumerable // Casts the elements of an IEnumerable to the specified type
	def OfType[TResult] : IEnumerable // Filters the elements of an IEnumerable based on a specified type
}

trait IQueryable[T] extends IEnumerable {
	type TResult = T
	
	def orderby[U](f: (TResult) => U) : IQueryable[T]
	def top(count: Int) : IQueryable[T]

	def :+(elem: TResult) : IQueryable[T]

	def empty : Boolean
	def count : Int 
	def fistOrDefault : Option[TResult]
	def aggregate[U](prop : TResult => U, op : List[TResult] => TResult) : IQueryable[T]
	def distinctBy[U](prop : TResult => U) : IQueryable[U]
	
	def foreach[U](body : (T) => U) : Unit
	def toList : List[T]
	def toString : String
}

class Linq_List[T] extends IQueryable[T] {
	type TContainer = List[TResult]
	var  coll : List[T] =  Nil//new List[T]

	def apply() : List[T] = coll 
	def AsQueryable : IQueryable[T] = this
	def Case[U] : IEnumerable = {
		var nc = new Linq_List[U]
		var ncoll : List[U] = Nil//new List[U]
		for (it <- coll) ncoll = ncoll :+ it.asInstanceOf[U]
		nc.coll = ncoll
		nc
	}
	def OfType[U] : IEnumerable = {
		val nc = new Linq_List[U]
		var ncoll : List[U] = Nil//new List[U]
		for (it <- coll if it.isInstanceOf[U]) yield ncoll = ncoll :+ it.asInstanceOf[U]
		nc.coll = ncoll
		nc
	}
	def orderby[U](f: (T) => U) : IQueryable[T] = {
		var nc = new Linq_List[T]
		val ls : List[T] = coll.sortBy(f)(new QueryOrdering[U])
		nc.coll = ls
		nc
	}
	def top(count: Int) : IQueryable[T] = {
		val nc = new Linq_List[T]
		var ncoll : List[T] = Nil//new List[T]
		var index = 0
		for (it <- coll) {
			if (index == count) nc
			else {
				index += 1 
				ncoll = ncoll :+ it
			}
		}
		nc.coll = ncoll
		nc
	}
	def :+(elem: T) : IQueryable[T] = {
		val nc = new Linq_List[T]
		var ncoll = coll :+ elem
		nc.coll = ncoll
		nc
	}
	def empty = coll.isEmpty
	def count = coll.length 
	def fistOrDefault = if (coll.isEmpty) None else Some(coll.head)
	def aggregate[U](prop : TResult => U, op : List[TResult] => TResult) : IQueryable[T] = {
		val distinct_query = distinctBy(prop)
		val nc = new Linq_List[T]
		var ncoll : List[T] = Nil//new Linq_List[T]
		for (it <- distinct_query) {
			val query = from[T] in coll where (x => prop(x) == it) select (x => x)
			ncoll = ncoll :+ op(query.toList)
		}
		nc.coll = ncoll
		nc
	}
	def distinctBy[U](prop : TResult => U) : IQueryable[U] = {
	    val query = from[TResult] in coll select (prop)
		val nc = new Linq_List[U]
		nc.coll = query.toList.distinct
		nc
	}
	  
	def foreach[U](body : (T) => U) = {
		coll.foreach(body)
	}
	def toList : List[T] = coll

	override def toString = coll.toString
}
