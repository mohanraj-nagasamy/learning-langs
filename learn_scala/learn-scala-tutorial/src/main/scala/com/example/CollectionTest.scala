package com.example

object CollectionTest {
  def main(args: Array[String]) {
    val toStream: Stream[Int] = (1 to 10).toList.toStream
    println("toStream = " + toStream)
  }
}
