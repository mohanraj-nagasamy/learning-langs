package com.example

/**
 * Created by mohanraj.nagasamy on 11/3/15.
 */
object MainLazy {
  def maybeTwice2(b: Boolean, i: => Int) = {
    //    lazy val j = i
    val j = i
    if (b) j + j else 0
    i
  }


  def main(args: Array[String]) {

    val x = maybeTwice2(true, {
      println("hi");
      1 + 41
    })
    println("Before XX")
    println("x = " + x)

  }

}
