package com.akka

import akka.actor._
import akka.actor.Actor.Receive

object MonitoringDemo {

  class Monitor(developerRef: ActorRef) extends Actor {
    override def preStart() = {
      context.watch(developerRef)
      println("Monitor preStart hook....")
    }

    override def preRestart(reason: Throwable, message: Option[Any]) = {
      println("Monitor preRestart hook...")
      super.preRestart(reason, message)
    }

    override def postRestart(reason: Throwable) = {
      println("Monitor postRestart hook...")
      super.postRestart(reason)
    }

    override def postStop() = {
      println("Monitor postStop...")
    }

    override def receive: Receive = {
      case Terminated =>
        context.stop(self)
    }
  }

  class Developer extends Actor {
    override def preStart() = {
      println("  -  Developer preStart hook....")
    }

    override def preRestart(reason: Throwable, message: Option[Any]) = {
      println("  -  Developer preRestart hook...")
      super.preRestart(reason, message)
    }

    override def postRestart(reason: Throwable) = {
      println("  -  Developer postRestart hook...")
      super.postRestart(reason)
    }

    override def postStop() = {
      println("  -  Developer postStop...")
    }

    override def receive: Actor.Receive = {
      case msg =>
        println(s"  -  Developer.received ${msg}");
        context.stop(self)
    }
  }

  def main(args: Array[String]) {
    val actorSystem: ActorSystem = ActorSystem.create("monitoring-demo")
    val developer: ActorRef = actorSystem.actorOf(Props[Developer])

    val monitor: ActorRef = actorSystem.actorOf(Props(new Monitor(developer)), "monitoring")

    developer ! "Hellow"
  }
}
