package cache

class SplunkCacheFacade {
	private def all = List("raw", "raw_yello", "endpoint", "query", "search_insignt")
  
	def apply(name : String) : SplunkCache = name match {
	  case "raw" => SplunkRawDataCache
	  case "raw_yello" => SplunkRawYelloDataCache
	  case "endpoint" => SplunkEndPointCache
	  case "query" => SplunkQueryLocationCache
	  case "search_insignt" => SplunkRequestCache
	  case _ => ???
	}

	def cleanAll = for (it <- all) apply(it).clearCache
	def initAll = for (it <- all) apply(it).initCache
	def synchonaizeAll(xs : String*) = for (it <- all) if (xs.contains(it)) apply(it).synchonaizeCache
}
