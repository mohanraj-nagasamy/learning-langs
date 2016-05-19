package com.akka

import akka.actor.{ActorRef, Props, ActorSystem, Actor}
import akka.actor.Actor.Receive
import akka.util.Timeout

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.{FiniteDuration, Duration}

object TestSimpleActor {

  case object Increment

  case object Decrement

  case object GetCount

  class Counter extends Actor {
    var counter = 0

    override def receive: Receive = {
      case Increment =>
        counter += 1
      case Decrement =>
        counter -= 1
      case GetCount =>
        sender() ! counter
    }
  }


  def main(args: Array[String]) {
    import akka.pattern.ask
    import ExecutionContext.Implicits.global
    implicit val timeout = Timeout(FiniteDuration(1, "sec"))

    val actorSystem: ActorSystem = ActorSystem("counter")
    val counter: ActorRef = actorSystem.actorOf(Props[Counter])

    counter ! Increment
    counter ! Increment

    (counter ? GetCount).map((value: Any) => {
      println("value = " + value)
    })
  }
}
