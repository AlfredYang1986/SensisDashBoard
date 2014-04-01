package errorreport

object SensisErrorFacade {
	def apply(f: () => Unit) = {
		try {
		  f()
		} catch {
		  case ex: SensisError => println(ex.message)
		} finally {
		  println("Exiting")
		}
	}
}