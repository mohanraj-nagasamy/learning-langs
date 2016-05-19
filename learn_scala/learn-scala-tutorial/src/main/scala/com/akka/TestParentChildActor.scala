package com.akka

import akka.actor._
import akka.actor.Actor.Receive

object TestParentChildActor {

  case object GetChildAge

  case object GetChildName

  case object GetParentName

  class Child(parentRef: ActorRef) extends Actor {
    override def receive: Receive = {
      case GetChildAge =>
        println("Child.received GetChildAge ")
        parentRef ! 4
      case GetChildName =>
        println("Child.received GetChildName ")
        parentRef ! "John"
    }
  }

  class Parent(childMaker: ActorRefFactory => ActorRef) extends Actor {
    val childRef = childMaker(context)

    override def receive: Actor.Receive = {
      case GetParentName =>
        println("Parent.received GetParentName")
        "Joe"
      case GetChildAge =>
        println("Parent.received GetChildAge")
        childRef ! GetChildAge

      case GetChildName =>
        println("Parent.received GetChildName")
        childRef ! GetChildName
    }
  }

  def main(args: Array[String]) {
    val system: ActorSystem = ActorSystem("parent-child-test")
//    val parent = system.actorOf(Props(classOf[Parent], (f: ActorRefFactory) => {
//      f.actorOf(Props(new Child(parent)))
//    }))
//
//    parent ! GetChildAge
  }
}
