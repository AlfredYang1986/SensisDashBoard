package unit_test

import query._
import com.mongodb.casbah.Imports._

object alfred_linq_test extends App {
//	var ls: List[Int] = List(6, 5, 4, 3, 2, 1)
//	
//	var query = from[Int] in 
//				ls where (x => x % 2 ==0) select
//				{
//	  				x => 
//	  				  var y = new String()
//	  				  y += "%d".format(x);
//	  				  y
//				}
//	
//	println(query)
//	println(query.orderby(x => x))
//	println(query.orderby(x => x).top(2))
//	
//	var q = from db() in "Alfred" where ("x" -> 5.asInstanceOf[Object]) select 
//			{ 
//				x => 
//				  new QueryTestDome(x.as[String]("foo"), x.as[Int]("x"), x.as[Double]("y"))
//			}
//
//	println(q)
//	println(q.orderby(x => x.y))
//	println(q.orderby(x => x.y).top(2))
//
//	var q1 = from db() in "Alfred" where ("x" -> 5.asInstanceOf[Object], "foo" -> "bar") select 
//			 { 
//				x => 
//				  new QueryTestDome(x.as[String]("foo"), x.as[Int]("x"), x.as[Double]("y"))
//			 }
//
//	println(q1)
//	println(q1.orderby(x => x.y))
//	println(q1.orderby(x => x.y).top(2))

//	var q2 = from db() in "Alfred" where ("x" -> 5.asInstanceOf[Object], ("y" $lt 50 $gt 20) ) select 
//			 { 
//				x => 
//				  new QueryTestDome(x.as[String]("foo"), x.as[Int]("x"), x.as[Double]("y"))
//			 }

//	println(q2)
//	println(q2.orderby(x => x.y))
//	println(q2.orderby(x => x.y).top(2))

    val start = 40000
    val end   = 42000
    val b = 1
    val e = 2
    var query_test = from db () in "splunkdata" where ("days" $gte start $lt end) select
//    var query_test = from db () in "splunkdata" where ("days" $lte end) select
//    var query_test = from db () in "splunkdata" where ("days" $gte end) select
//    var query_test = from db () in "splunkdata" where ("days" $gte start $lte end) select
//    var query_test = from db () in "splunkdata" where ("search" $gte b $lte e) select
      {
        x => x
      }
    
    println(query_test)
}