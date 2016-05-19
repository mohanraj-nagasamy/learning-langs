package com.akka

import java.util.concurrent.TimeUnit

import akka.actor._
import akka.actor.Actor.Receive
import akka.dispatch.Mailboxes

import scala.concurrent.duration.Duration

object Helloworld {

  class HelloWorldActor extends Actor {

    override def aroundReceive(receive: Actor.Receive, msg: Any): Unit = {
      println("#" * 100)
      println("HelloWorldActor.aroundReceive")
      println("receive = " + receive)
      println("msg = " + msg)

      super.aroundReceive(receive, msg)
      println("-" * 100)
    }

    override def aroundPreStart(): Unit = {
      println("HelloWorldActor.aroundPreStart")
      super.aroundPreStart()
    }

    override def aroundPostStop(): Unit = {
      println("HelloWorldActor.aroundPostStop")
      super.aroundPostStop()
    }

    override def aroundPreRestart(reason: Throwable, message: Option[Any]): Unit = {
      println("HelloWorldActor.aroundPreRestart")
      super.aroundPreRestart(reason, message)
    }

    override def aroundPostRestart(reason: Throwable): Unit = {
      println("HelloWorldActor.aroundPostRestart")
      super.aroundPostRestart(reason)
    }

    override def supervisorStrategy: SupervisorStrategy = {
      println("HelloWorldActor.supervisorStrategy")
      super.supervisorStrategy
    }

    @throws[Exception](classOf[Exception])
    override def preStart(): Unit = {
      println("HelloWorldActor.preStart");
      super.preStart()
    }

    @throws[Exception](classOf[Exception])
    override def postStop(): Unit = {
      println("HelloWorldActor.postStop");
      super.postStop()
    }

    @throws[Exception](classOf[Exception])
    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      println("HelloWorldActor.preRestart");
      super.preRestart(reason, message)
    }

    @throws[Exception](classOf[Exception])
    override def postRestart(reason: Throwable): Unit = {
      println("HelloWorldActor.postRestart");
      super.postRestart(reason)
    }

    override def unhandled(message: Any): Unit = {
      println("HelloWorldActor.unhandled");
      super.unhandled(message)
    }

    override def receive: Receive = {
      case "hello" => {
        println("HelloWorld")
        TimeUnit.SECONDS.sleep(5)
        println("Done hello")
        //        sender() ! "Done hello"
        sender() ! PoisonPill
        //        sender() ! Kill
      }
      case _ => {
        println("I don't know you")
        TimeUnit.SECONDS.sleep(5)
        println("Done default")
        //        sender() ! "Done default"
        sender() ! PoisonPill
      }
    }
  }

  def main(args: Array[String]) {
    val actorSystem: ActorSystem = ActorSystem.create("HelloWorld")
    val hello: ActorRef = actorSystem.actorOf(Props(classOf[HelloWorldActor]))


    actorSystem.actorSelection("")

    //    val hello1: ActorRef = actorSystem.actorOf(Props(classOf[HelloWorldActor]))
    println("Start sending")
    hello ! "hello"
    hello ! "asdfasfdsa"
    //    hello1 ! "asdf"
    println("Finish sending")

  }
}
