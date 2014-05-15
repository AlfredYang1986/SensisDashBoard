/**
 * one day data insert once
 * Created by Alfred Yang
 * 14th May, 2014
 */

package cache

trait SplunkCache {
	def initCache
	def clearCache
	def synchonaizeCache
	def isClean : Boolean
	def addRecord(d : Int, k : String, m : String)
}