/**
 * Just print the query result for testing
 * Create By Alfred Yang
 */

package resultphrase

object PrintlnResultHandle extends ResultHandle {
	def apply(result: String) = println(result)
}