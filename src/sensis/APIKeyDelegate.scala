package sensis

object APIKeyDelegateDispatch {
	def UrlKeyValue(name: String, delegate: String, args: Map[String, String]) : String = {
		val func : String = delegate.substring(0, delegate.indexOf("("))
		val ls : List[String] = delegate.substring(delegate.indexOf("(") + 1, delegate.indexOf(")")).split(",").toList
		func match {
		  case "copy" => {
			  val a = args.get(ls.head).get
			  copy(name, a)
		  }
		  case "md5" => { null }
		  case _ => null
		}
	}
	
	private def copy(name: String, value: String) : String = name + "=" + value
}