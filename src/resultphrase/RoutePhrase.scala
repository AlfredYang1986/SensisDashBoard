package resultphrase

object RoutePhraseSplunk {
	def phraseMethodName(s : String) : String = {
		if (s.contains('?')) s.substring(s.indexOf('/') + 1, s.indexOf('?'))
		else s.substring(s.indexOf('/') + 1, s.lastIndexOf(' '))
	}
	def phraseArguments(s : String) : Map[String, String]= {
		var re: Map[String, String] = Map.empty
		if (!s.contains('?'))  return re
		
		val sq = s.substring(s.indexOf('?') + 1, s.lastIndexOf(' '))
		val sl : List[String] = sq.split("&").toList
		for (it <- sl) {
		  if (!it.isEmpty()) {
			  val s_1 = it.substring(0, it.indexOf('='))
			  val s_2 = it.substring(it.indexOf('=') + 1)
			  re += (it.substring(0, it.indexOf('=')) -> it.substring(it.indexOf('=') + 1))
		  }
		}
		re
	}
}