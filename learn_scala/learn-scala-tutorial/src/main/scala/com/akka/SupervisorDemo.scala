package com.akka

import akka.actor.SupervisorStrategy.{Escalate, Restart, Stop, Resume}
import akka.actor._
import akka.actor.Actor.Receive

import scala.concurrent.duration.Duration

object SupervisorDemo {

  case object ResumeException extends Exception

  case object StopException extends Exception

  case object RestartException extends Exception

  case object ResumeMsg

  case class Msg(msg: String)

  case object StopMsg

  case object RestartMsg

  class Manager extends Actor {
    val developerRef = context.actorOf(Props[Developer], "Developer")


    override def supervisorStrategy: SupervisorStrategy = {
      OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = Duration("10 sec"), loggingEnabled = true) {
        case ResumeException => Resume
        case StopException => Stop
        case RestartException => Restart
        case _: Exception => Escalate
      }
    }

    override def preStart() = {
      println("Manager preStart hook....")
    }

    override def preRestart(reason: Throwable, message: Option[Any]) = {
      println("Manager preRestart hook...")
      super.preRestart(reason, message)
    }

    override def postRestart(reason: Throwable) = {
      println("Manager postRestart hook...")
      super.postRestart(reason)
    }

    override def postStop() = {
      println("Manager postStop...")
    }

    override def receive: Receive = {
      case msg =>
        println(s"Manager.received msg ${msg}")
        developerRef ! msg
//        println("Sending message to developerRef = " + developerRef)
    }
  }

  class Developer extends Actor {
    override def preStart() = {
      println("Developer preStart hook....")
    }

    override def preRestart(reason: Throwable, message: Option[Any]) = {
      println("Developer preRestart hook...")
      super.preRestart(reason, message)
    }

    override def postRestart(reason: Throwable) = {
      println("Developer postRestart hook...")
      super.postRestart(reason)
    }

    override def postStop() = {
      println("Developer postStop...")
    }


    override def receive: Actor.Receive = {
      case Msg(s) =>
        println(s"Developer.received ${s} " + self)
      case RestartMsg =>
        println("Developer.received RestartMsg")
        throw RestartException
      case ResumeMsg =>
        println("Developer.received ResumeMsg")
        throw ResumeException
      case StopMsg =>
        println("Developer.received StopMsg")
        throw StopException
      case _ =>
        println("Developer.received default")
        throw new Exception
    }
  }

  def main(args: Array[String]) {
    val actorSystem: ActorSystem = ActorSystem.create("Manager")

    val manager: ActorRef = actorSystem.actorOf(Props[Manager])

    //    manager ! Msg(" 1111111111 ")
    //    manager ! RestartMsg
    //    manager ! Msg(" 2222222222 ")

    //    manager ! Msg(" 1111111111 ")
    //    manager ! ResumeMsg
    //    manager ! Msg(" 2222222222 ")

    //    manager ! Msg(" 1111111111 ")
    //    manager ! StopMsg
    //    manager ! Msg(" 2222222222 ")

    manager ! Msg(" 1111111111 ")
    manager ! "defaultMsg"
    manager ! Msg(" 2222222222 ")

    //    val manager2: ActorRef = actorSystem.actorOf(Props[Manager])
    //    manager2 ! Msg(" 1111111111 ")
    //    manager2 ! "defaultMsg"
    //    manager2 ! Msg(" 2222222222 ")
  }
}
