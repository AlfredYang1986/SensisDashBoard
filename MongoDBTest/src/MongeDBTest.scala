
import com.mongodb.casbah.Imports._

object mongodbTest extends App {

	// Connecting to MongoDB
	val mongoConn = MongoConnection()
	// get DB
	val mongoColl = mongoConn("Alfred_Test")("Alfred")
	// get DB collection or table
	
			
	var new_obj : DBObject = MongoDBObject("foo" -> "bar", "x" -> 5, "y" -> 238.1)
	// add to table
	mongoColl += new_obj
	new_obj = MongoDBObject("foo" -> "a", "x" -> 5, "y" -> 2.1)
	mongoColl += new_obj
	new_obj = MongoDBObject("foo" -> "b", "x" -> 5, "y" -> 23.1)
	mongoColl += new_obj
	new_obj = MongoDBObject("foo" -> "c", "x" -> 5, "y" -> 29.1)
	mongoColl += new_obj
	new_obj = MongoDBObject("foo" -> "d", "x" -> 3, "y" -> 999.1)
	mongoColl += new_obj
	new_obj = MongoDBObject("foo" -> "e", "x" -> 5, "y" -> 123.1)
	mongoColl += new_obj
	new_obj = MongoDBObject("foo" -> "far", "x" -> 4, "y" -> 456.1)
	mongoColl += new_obj

	// query
	val query : DBObject = MongoDBObject("x" -> 5)
	mongoColl.find(query).foreach { x => println(x) }
}