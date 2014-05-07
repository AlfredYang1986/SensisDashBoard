package unit_test

import java.util.Date
import org.joda.time.Days
import org.joda.time.DateTime

object DateTest extends App {
	val past = new Date(110, 5, 20); // June 20th, 2010
	val today = new Date(110, 6, 24); // July 24th 
	val days = Days.daysBetween(new DateTime(past), new DateTime(today)).getDays(); 
	
	println(days)
}