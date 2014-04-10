package resultphrase

object PrintlnResultHandle extends ResultHandle {
	def apply(result: String) = println(result)
}