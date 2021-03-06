/**
 * Error report
 * Created by Alfred Yang
 * 2nd April, 2014
 */

package errorreport

import scala.util.control.Exception._

abstract class SensisError extends Exception {
	def code : Int
	def message : String
	def description : String
}

object Error_Common extends SensisError {
	def code = -1
	def message = "Common Error"
	def description = ""
}

object Error_CallApiFail extends SensisError {
	def code = -2
	def message = "Call Sensis Api Error"
	def description = ""
}

object Error_PhraseJosn extends SensisError {
	def code = -3
	def message = "Phrase Josn Error"
	def description = ""
}

object Error_PhraseXML extends SensisError {
	def code = -4
	def message = "Phrase XML Error"
	def description = ""
}

object Error_ArgumentNotFound extends SensisError {
	def code = -5
	def message = "Required Function Argument Not Found"
	def description = ""
}