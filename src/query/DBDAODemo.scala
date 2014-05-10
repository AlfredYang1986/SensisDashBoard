package query

//case class SplunkDataDAODome (getByListingId: Int
//    , search: Int, serviceArea: Int, listingsInheadingInlocality: Int
//    , signleSearch: Int, appearance: Int, viewDetails: Int 
//    , topCategoriesInLocality: Int, categoriesInlocality: Int
//    , localitiesInState: Int, UserKey: String, days: Long)
    
case class QueryTestDome(foo: String, x: Int, y: Double) {
  
	override def toString() = {
		"%s: x=%d, y=%f\n".format(foo, x, y)
	}
}