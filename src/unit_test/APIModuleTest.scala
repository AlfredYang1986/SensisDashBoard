package unit_test

import errorreport._
import sensis.APIFactory

object APITest extends App {
	SensisErrorFacade(APIFactory.apply)
}