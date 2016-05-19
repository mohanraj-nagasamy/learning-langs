package com.excecises


object ForExpression {
  def concatenator(w1: String)(w2: String) = {
    w1 + " " + w2
  }

  def main(args: Array[String]) {
    val list: List[Any] = List(1, "One", 1.1)

    //    val ints: List[Int] = for {
    //      (number: Int) <- list
    //    } yield number
    //    println("ints = " + ints)
    val xs = List("foo", "abc", 5.5f)

    //    val res = for ((x: String) <- xs) yield x
    //
    //    println("res = " + res)

    val c: (String) => String = concatenator(_: String)("Mohan")
    println("c = " + c)
    val c1: String = c("Mohan")
    println("c1 = " + c1)
  }
}
