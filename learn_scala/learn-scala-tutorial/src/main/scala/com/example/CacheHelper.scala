package com.example

class CacheHelper {


  def createCommand(cacheKey: String, func: => Long): Command = {
    println("cacheKey = " + cacheKey)
    println("func = " + func)
    new Command(cacheKey, func)
    //    Mock this method
  }

  def createCommand123(cacheKey: String, func: () => Long): Command = {
    println("cacheKey = " + cacheKey)
    println("func = " + func)
    new Command(cacheKey, func())
    //    Mock this method
  }

  def getOrElse(cacheKey: String)(func: () => Long): Long = {

    println("circuitBreakerEnabled = " + isCircuitBreakerEnabled)
    if (isCircuitBreakerEnabled) {
      val createCommand1: Command = createCommand(cacheKey, func())
      println("createCommand1 = " + createCommand1)
      createCommand1.execute()
    }
    else {
      util.Random.nextInt()
    }
  }

  def isCircuitBreakerEnabled: Boolean = {
    println("CacheHelper.isCircuitBreakerEnabled")
    false
  }
}
