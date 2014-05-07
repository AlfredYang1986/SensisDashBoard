package query.property

trait Property {
	type A
	val name : String
	def set(value : A) : Unit
	def get : A
}

case class CommonProperty[T](name : String) extends Property {
	type A = T 
	var _v : A  = null.asInstanceOf[A]
	def set(value : A) = _v = value
	def get : A = _v
	
	override def toString = name + ": " + _v.toString
}