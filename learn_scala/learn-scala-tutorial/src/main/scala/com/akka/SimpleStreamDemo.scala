package com.akka

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Flow, Source}

object SimpleStreamDemo {

  def main(args: Array[String]) {
    implicit val system: ActorSystem = ActorSystem("stream-test")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    //Source
    val input = Source(1 to 100)

    //Flow
    val normalize = Flow[Int].map((i: Int) => i * 2)

    //Output
    val output = Sink.foreach(println)

    input.via(normalize).runWith(output)
  }
}
