package unit_test

import java.util.regex.Matcher
import java.util.regex.Pattern

object RegexTest extends App {

  var str: String = "carlton+north+Melbourne+VIC"
  // str = str.count(_ == "1")
  println(str.count(_ == '+'))

  
  
  var location = str
  if (location.count(_ == '+') > 1)
    location = (location.replaceFirst("\\+", " ").replace('+', ',').trim).toLowerCase
  else
    (location.replace('+', ',').trim).toLowerCase

  println(location)
  
  

  var str22: String = "Tovo+A+%26+,.,,#D"
  val result: String = java.net.URLDecoder.decode(str22, "UTF-8")

  println("result :   " + result)

//  val pattern: Pattern = Pattern.compile("\\W");
//  val matcher: Matcher = pattern.matcher(result);
//  if (matcher.find()) {
//    str22 = matcher.replaceAll(" ")
//  }

  println("str22 after matching :   " + str22)

  str22.split(" ").foreach(x => if (x != "") println(x.trim()))

  
  // Old school Regex matching for eliminating special characters.
  val decodeResult: String = java.net.URLDecoder.decode(str22, "UTF-8")
  var userQueries: Array[String] = Array.empty
  val pattern: Pattern = Pattern.compile("\\W");
  val matcher: Matcher = pattern.matcher(decodeResult)
  if (matcher.find()) {
    userQueries = (matcher.replaceAll(" ")).split(" ")
  }

  userQueries.foreach(println)

}