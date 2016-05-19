package com.akka

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object PingPongDemo {

  case object Ping

  case object Start

  case object Stop

  case object Pong

  class Table(players: List[ActorRef]) extends Actor {

    var index = 0

    def playerIndex = index % players.size

    override def postStop(): Unit = {
      println("Table.postStop")
    }

    override def receive: Receive = {
      case Start =>
        players(playerIndex) ! Ping
        index += 1
      case Stop =>
        println("Stop")
        players.foreach(p => context.stop(p))
        context.stop(self)
      case Ping =>
        TimeUnit.SECONDS.sleep(1)
        players(playerIndex) ! Pong
        index += 1
      case Pong =>
        TimeUnit.SECONDS.sleep(1)
        players(playerIndex) ! Ping
        index += 1
    }
  }

  class Player(name: String) extends Actor {

    @scala.throws[Exception](classOf[Exception])
    override def postStop(): Unit = {
      println("Player.postStop")
    }

    override def receive: Receive = {
      case Ping =>
        println(name + " Ping")
        sender() ! Ping
      case Pong =>
        println(name + " Pong")
        sender() ! Pong
    }
  }

  def main(args: Array[String]) {
    val actorSystem: ActorSystem = ActorSystem.create("ping-pong")

    val player1: ActorRef = actorSystem.actorOf(Props(new Player("player1")), "player1")
    val player2: ActorRef = actorSystem.actorOf(Props(new Player("player2")), "player2")
    val player3: ActorRef = actorSystem.actorOf(Props(new Player("player3")), "player3")
    val player4: ActorRef = actorSystem.actorOf(Props(new Player("player4")), "player4")

    val table: ActorRef = actorSystem.actorOf(Props(new Table(List(player1, player2, player3, player4))), "table")

    table ! Start
    TimeUnit.SECONDS.sleep(10)
    table ! Stop
    actorSystem.te
  }

}
