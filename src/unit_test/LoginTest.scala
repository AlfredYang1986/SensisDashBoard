package unit_test

import login.UserLogin

object loginTes extends App {
//	println(UserLogin.authorization("Pete", "8888"))
//	println(UserLogin.authorization("Pete", "123456"))
//	println(UserLogin.changePassword("Pete", "123456"))
//	println(UserLogin.authorization("Pete", "123456"))
	println(UserLogin.addAdmin("Alfred", "123456"))
	println(UserLogin.authorization("Alfred", "123456"))
	println(UserLogin.deleteAdmin("Alfred"))
	println(UserLogin.authorization("Alfred", "123456"))
}