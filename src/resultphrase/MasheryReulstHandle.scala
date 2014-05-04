package resultphrase

import jsondataparse.parser

object MasheryReulstHandle extends ResultHandle {
	def apply(result: String) = {
		(parser.parse(result, List("result"))
		.get("result").get.asInstanceOf[Map[String, Any]]
		.get("items").get.asInstanceOf[List[Map[String, Any]]]).map { x => 
		  	println(x)
			// 1. update if existed
		  	// 2. otherwise insert
		}
	}
}