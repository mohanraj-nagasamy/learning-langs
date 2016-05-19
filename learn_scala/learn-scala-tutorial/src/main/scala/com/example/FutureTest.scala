package com.example

import java.util.concurrent.TimeUnit

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object FutureTest {
  def main(args: Array[String]) {
    println("FutureTest$.main")

    val f = Future {
//      TimeUnit.SECONDS.sleep(2)
      2 / 0
    }
//    println("f = " + f.value.get.get)
    val result = Await.result(f, 1 seconds)

    println("f = " + result)
  }

}
