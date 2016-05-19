package com.example

import scala.concurrent.Future

object Hello {

  case class User(name: String)

  Thread.`yield`()


  def main(args: Array[String]): Unit = {
    val first = Future(Option(1))
    val second = Future(Option(2))

    val sum = for {
      firstResult <- first
      secondResult <- second
    } yield {
      firstResult + secondResult
    }

  }

}

