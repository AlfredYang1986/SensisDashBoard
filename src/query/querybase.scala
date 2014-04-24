package query

trait IQueryable

class ALINQ[T] {
	var w : (T) => Boolean = null
	var ls : List[T] = Nil
  
	def in(l: List[T]) : ALINQ[T] = {
		ls = l
		this
	}

	def where(f: (T) => Boolean) : ALINQ[T] = {
		w = f
		this
	}
	
	def select() : List[T] = {
		for {
			i <- ls
			if (w(i))
		} yield i
	}
}

object from {
	def apply[T]() : ALINQ[T] = {
		var r = new ALINQ[T]
		return r
	}
}