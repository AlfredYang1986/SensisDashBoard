package unit_test

import query.from

object alfred_linq_test extends App {
	var ls: List[Int] = List(1, 2, 3, 4, 5, 6)
	
	var query = from[Int] in ls where (x => x % 2 ==0) select

	println(query)
}