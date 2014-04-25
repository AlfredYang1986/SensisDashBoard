package unit_test

import query._

object alfred_linq_test extends App {
	var ls: List[Int] = List(1, 2, 3, 4, 5, 6)
	
	var query = from[Int] in 
				ls where (x => x % 2 ==0) select 
				{
	  				x => 
	  				  var y = new String()
	  				  y += "%d".format(x);
	  				  y
				}
	
	println(query)
	
	var q = from db() in "Alfred" where ("x" -> 5.asInstanceOf[Object]) select 
			{ 
				x => 
				  new QueryTestDome(x.as[String]("foo"), x.as[Int]("x"), x.as[Double]("y"))
			}

	println(q)

	var q1 = from db() in "Alfred" where ("x" -> 5.asInstanceOf[Object], "foo" -> "bar") select 
			 { 
				x => 
				  new QueryTestDome(x.as[String]("foo"), x.as[Int]("x"), x.as[Double]("y"))
			 }

	println(q1)
}