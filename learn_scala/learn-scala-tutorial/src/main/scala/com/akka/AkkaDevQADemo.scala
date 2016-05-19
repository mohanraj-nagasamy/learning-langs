package com.akka

import akka.actor.{ActorRef, Props, ActorSystem, Actor}
import akka.actor.Actor.Receive
import akka.event.{LoggingAdapter, Logging}

import scala.util.Random


object AkkaDevQADemo {

  class Developer(tester: ActorRef) extends Actor {
    val log: LoggingAdapter = Logging.getLogger(context.system, this)

    override def receive: Actor.Receive = {
      case f: NewFeature =>
        log.info("Working on a new feature " + f.name)
        tester ! f
      case Bug =>
        log.info("Fixing a bug")
        sender() ! Fixed
      case _ =>
        log.info("Watching youtube")
    }
  }

  class Tester extends Actor {
    val log: LoggingAdapter = Logging.getLogger(context.system, this)

    override def receive: Receive = {
      case f: NewFeature =>
        log.info("Testing a new feature " + f.name)
        val isBug = Random.nextBoolean()
        if (isBug) {
          log.info(s"Found a bug in '${f.name}' feature")
          sender() ! Bug
        }
      case Fixed => log.info("Verifying the test")
      case _ => log.info("Doing nothing")

    }
  }

  case class NewFeature(name: String)

  object Bug

  object Fixed

  private val system: ActorSystem = ActorSystem.create("octanner")

  def main(args: Array[String]) {
    val tester: ActorRef = system.actorOf(Props(new Tester), "tester")
    val developer: ActorRef = system.actorOf(Props(new Developer(tester)), "developer")

    (1 to 10).foreach((i: Int) => {
      developer ! NewFeature("e-card")
      println("#" * 100)
      Thread.sleep(3000)
    })
  }
}
