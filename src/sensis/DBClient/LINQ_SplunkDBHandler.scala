package sensis.DBClient

import sensis.DBClient.DAO.SplunkDataDAO

/*
 * TODO: Stub class to handle splunk data collection.
 */
class LINQ_SplunkDBHandler {

  /**
   * Retrieve a single user object with the function calls summed-up.
   */
  def getEachUserByKey(startDate: String, endDate: String, logSourceName: String, ukey: String): List[SplunkDataDAO] = {
    null
  }

  /**
   * Retrieve a list of distinct users keys.
   */
  def getAllDistinctUserKeys(startDate: String, endDate: String, logSourceName: String): List[String] = {
    null
  }

  /**
   * Retrieve distinct users with the function calls summed-up.
   */
  def getDistinctUsers(startDate: String, endDate: String, logSourceName: String): List[SplunkDataDAO] = {
    null
  }

  /**
   * Return the top ten distinct users.
   */
  def getTopTenUsers(startDate: String, endDate: String, logSourceName: String): List[SplunkDataDAO] = {
    null
  }

  /**
   * Provide the function usage for each distinct function, in descending order.
   */
  def getFunctionUsage(startDate: String, endDate: String, logSourceName: String) {

  }

  def getIntDays(date: String): Int = {
    0
  }

  def sortForTopTen(numOfCalls: List[SplunkDataDAO]): List[SplunkDataDAO] = {
    null
  }
}