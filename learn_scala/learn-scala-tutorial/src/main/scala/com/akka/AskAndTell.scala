package com.akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object AskAndTell {

  case class User(name: String, email: String)

  case class NewUser(user: User)

  case class CheckUser(user: User)

  case class WhiteListed(user: User)

  case class BlackListed(user: User)

  case class StoreUser(user: User)

  object Recorder {

  }

  class Checker extends Actor {
    val blackListedList = List(
      User("admin", "admin@octanner.com")
    )

    override def receive: Actor.Receive = {
      case CheckUser(user) =>
        if (blackListedList.contains(user)) {
          sender ! BlackListed(user)
        } else {
          sender ! WhiteListed(user)
        }

      case _ => println("Checker.received default");
    }
  }

  class Storage extends Actor {
    override def receive: Actor.Receive = {
      case StoreUser(user) => println(s"Storage.received ${user}");
      case _ => println("Storage.receive default");
    }
  }

  class Recorder(checker: ActorRef, storage: ActorRef) extends Actor {
    implicit val timeout = Timeout(5 seconds)

    override def receive: Receive = {
      case NewUser(user) =>
        println(s"Recorder receives NewUser for ${user}")
        //        Patterns.ask(checker, user, Timeout(5 seconds))
        checker.ask(CheckUser(user)).map {
          case WhiteListed(user) =>
            println("user = " + user + " is WhiteListed")
            storage ! StoreUser(user)
          case BlackListed(user) =>
            println("user = " + user + " is BlackListed")
        }
    }
  }

  def main(args: Array[String]) {
    val actorSystem: ActorSystem = ActorSystem.create("AskAndTell")

    val checker = actorSystem.actorOf(Props[Checker], "checker")
    val storage = actorSystem.actorOf(Props[Storage], "storage")

    val recorder = actorSystem.actorOf(Props(new Recorder(checker, storage)), "recorder")

    recorder ! NewUser(User("test", "test@octanner.com"))
    recorder ! NewUser(User("admin", "admin@octanner.com"))

    //actorSystem.shutdown()
  }
}
