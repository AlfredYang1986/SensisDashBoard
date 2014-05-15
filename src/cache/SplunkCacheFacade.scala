package cache

class SplunkCacheFacade {
	private def all = List("raw", "endpoint", "query")
  
	def apply(name : String) : SplunkCache = name match {
	  case "raw" => SplunkRawDataCache
	  case "endpoint" => SplunkEndPointCache
	  case "query" => SplunkQueryLocationCache
	  case _ => ???
	}

	def cleanAll = for (it <- all) apply(it).clearCache
	def initAll = for (it <- all) apply(it).initCache
	def synchonaizeAll = for (it <- all) apply(it).synchonaizeCache
}