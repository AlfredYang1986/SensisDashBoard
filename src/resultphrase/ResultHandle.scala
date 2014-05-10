/**
 * interface for handle result
 * Created by Alfred Yang
 */

package resultphrase

trait ResultHandle {
	def apply(result: String) : Unit
}