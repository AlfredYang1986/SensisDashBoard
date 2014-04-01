package errorreport

import scala.util.control.Exception._

abstract class SensisError extends Exception {
	def code : Int
	def message : String
	def description : String
}

class Error_Common extends SensisError {
	def code = -1
	def message = "Common Error"
	def description = ""
}

class Error_CallApiFail extends SensisError {
	def code = -2
	def message = "Call Sensis Api Error"
	def description = ""
}

class Error_PhraseJosn extends SensisError {
	def code = -3
	def message = "Phrase Josn Error"
	def description = ""
}

class Error_PhraseXML extends SensisError {
	def code = -4
	def message = "Phrase XML Error"
	def description = ""
}