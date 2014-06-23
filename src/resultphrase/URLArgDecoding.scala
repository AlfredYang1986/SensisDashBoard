/**
 * Because there is some performance issues with the java.net.url.decode
 * And I do not know the reasons
 * So I provide a double cache algorithm to analysis the query string, and change it to origin
 * Created by Alfred Yang
 * 9th May, 2014 
 */

package resultphrase

object URLArgDecoding {
	
	def decoding(input : String) : String = {
		var index = 0
		var reVal : StringBuilder = new StringBuilder
		while (index < input.length()) {
			if (input.charAt(index) == '%') {
				_level.get(input.subSequence(index, index + 2).toString().toUpperCase()) match {
				  case Some(e) => reVal.append(e.asInstanceOf[Char]); index = 3 + index
				  case none => ;
				}
			} else if (input.charAt(index) == '+') reVal.append(' ')
			else reVal.append(input.charAt(index)); index = 1 + index
		}
		reVal.toString
	}

	private val _level : Map[String, Char] = Map(
	    ("%20" -> ' '), ("%21" -> '!'), ("%22" -> '"'),
	    ("%23" -> '#'), ("%24" -> '$'), ("%25" -> '%'),
	    ("%26" -> '&'), ("%27" -> '''), ("%28" -> '('),
	    ("%29" -> ')'), ("%2A" -> '*'), ("%2B" -> '+'),
	    ("%2C" -> ','), ("%2D" -> '-'), ("%2E" -> '.'),
	    ("%2F" -> '/'), 
	    ("%3A" -> ':'), ("%3B" -> ';'), ("%3C" -> '<'),
	    ("%3D" -> '='), ("%3E" -> '>'), ("%3F" -> '?'),
	    ("%5B" -> '['), ("%5C" -> '\\'), ("%5D" -> ']'),
	    ("%5E" -> '^'), ("%5F" -> '_'), ("%60" -> '`'),
	    ("%7B" -> '{'), ("%7C" -> '|'), ("%7D" -> '}'),
	    ("%7E" -> '~') 
	)
}