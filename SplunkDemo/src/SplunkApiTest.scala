
import com.splunk._
import org.apache.commons.io.IOUtils

object SplunkApiTest extends App {
	
	// Create a map of arguments and add login parameters
	val loginArgs : ServiceArgs = new ServiceArgs()
	loginArgs.setUsername("admin")
	loginArgs.setPassword("Abcde196125")
	loginArgs.setHost("localhost")
	loginArgs.setPort(8089)
	
	// Create a Service instance and log in with the argument map
	val service : Service = Service.connect(loginArgs)

	// get user
	for (user <- service.getUsers().values().toArray()) println(user.asInstanceOf[User].getName())

	// get application
	for (user <- service.getApplications().values().toArray()) println(user.asInstanceOf[Application].getName())
	
	println(IOUtils.toString(service.export("search password")))
}