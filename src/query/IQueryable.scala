package query

import scala.collection.mutable.LinkedList

trait IEnumerable {
	type TResult
	type TContainer
	
	def AsQueryable : IQueryable[TResult]
	def Case[TResult] : IEnumerable // Casts the elements of an IEnumerable to the specified type
	def OfType[TResult] : IEnumerable // Filters the elements of an IEnumerable based on a specified type
}

trait IQueryable[T] extends IEnumerable {
	type TResult = T
	def AsQueryable : IQueryable[TResult]
	def Case[T] : IEnumerable 
	def OfType[T] : IEnumerable
	
	def orderby[U](f: (TResult) => U) : IQueryable[T]
	def top(count: Int) : IQueryable[T]

	def :+(elem: TResult) : IQueryable[T]
}

class Linq_List[T] extends IQueryable[T] {
	type TContainer = LinkedList[TResult]
	var  coll =  new LinkedList[T]

	def apply() : LinkedList[T] = coll 
	def AsQueryable : IQueryable[T] = this
	def Case[U] : IEnumerable = {
		var nc = new Linq_List[U]
		var ncoll = new LinkedList[U]
		for (it <- coll) ncoll = ncoll :+ it.asInstanceOf[U]
		nc.coll = ncoll
		nc
	}
	def OfType[U] : IEnumerable = {
		val nc = new Linq_List[U]
		var ncoll = new LinkedList[U]
		for (it <- coll if it.isInstanceOf[U]) yield ncoll = ncoll :+ it.asInstanceOf[U]
		nc.coll = ncoll
		nc
	}
	def orderby[U](f: (T) => U) : IQueryable[T] = {
		var nc = new Linq_List[T]
		val ls : LinkedList[T] = coll.sortBy(f)(new QueryOrdering[U])
		nc.coll = ls
		nc
	}
	def top(count: Int) : IQueryable[T] = {
		val nc = new Linq_List[T]
		var ncoll = new LinkedList[T]
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
	
	override def toString = coll.toString
}
