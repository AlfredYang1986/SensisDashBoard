package unit_test

import query._
import query.helper._

object AlfredMasheryDataForTony extends App {
	val query = from db() in "masherydata" where
				MasheryHelper.queryByUsernameDB("debbie.mclennan") select
				MasheryHelper.queryMasheryDBOToQueryObject("first_name", "email")
				
	println(query)
}