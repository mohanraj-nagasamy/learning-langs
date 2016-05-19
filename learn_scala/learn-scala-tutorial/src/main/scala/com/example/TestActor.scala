package com.example

import akka.actor.{ActorRef, Props, ActorSystem, Actor}
import akka.actor.Actor.Receive

class SimpleActor extends Actor {
  override def receive: Receive = {
    case s: String => println("s = " + s)
    case i: Int => println("i = " + i)
    case _ => println("Unknown")
  }
}

object TestActor {
  def test: Receive = {
    case a: String => println("a = " + a)
  }

  def main(args: Array[String]) {
    test
    val system: ActorSystem = ActorSystem("testing-actor")
    val simpleActor: ActorRef = system.actorOf(Props[SimpleActor], "FirstActor")
    simpleActor.!("one")
    simpleActor.!(1)
    simpleActor.!(343.343)

    system.shutdown()
  }

}
