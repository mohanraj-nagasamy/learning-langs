package com.akka

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, Props, ActorSystem, Actor}
import akka.actor.Actor.Receive

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits._
object TimeoutAndBufferDemo {

  class TimeoutAndBufferActor1 extends Actor {
    private val arrayBuffer: ArrayBuffer[Int] = new ArrayBuffer[Int]


    override def preStart(): Unit = {
      super.preStart()
      context.setReceiveTimeout(Duration("2 sec"))
    }

    override def receive: Receive = {
      case num: Int =>
        println(s"TimeoutAndBufferActor1.received ${num}");
        arrayBuffer += num
        if (arrayBuffer.size == 10) {
          println("1) arrayBuffer = " + arrayBuffer)
          arrayBuffer.clear()
        }
      case akka.actor.ReceiveTimeout =>
        println("akka.actor.ReceiveTimeout Received")
        println("2) arrayBuffer = " + arrayBuffer)
        arrayBuffer.clear()

    }
  }

  class TimeoutAndBufferActor2 extends Actor {
    private val arrayBuffer: ArrayBuffer[Int] = new ArrayBuffer[Int]

    var tick = context.system.scheduler.scheduleOnce(2 seconds, self, akka.actor.ReceiveTimeout)

    override def receive: Receive = {
      case num: Int =>
        tick.cancel()
        tick = context.system.scheduler.scheduleOnce(2 seconds, self, akka.actor.ReceiveTimeout)
        println(s"TimeoutAndBufferActor2.received ${num}");
        arrayBuffer += num
        if (arrayBuffer.size == 10) {
          println("1) arrayBuffer = " + arrayBuffer)
          arrayBuffer.clear()
        }
      case akka.actor.ReceiveTimeout =>
        println("akka.actor.ReceiveTimeout Received")
        println("2) arrayBuffer = " + arrayBuffer)
        arrayBuffer.clear()
        tick.cancel()
    }
  }

  def main(args: Array[String]) {
    val actorSystem: ActorSystem = ActorSystem("TimeoutAndBufferDemo")
//    val bufferActor1: ActorRef = actorSystem.actorOf(Props[TimeoutAndBufferActor1])
    val bufferActor2: ActorRef = actorSystem.actorOf(Props[TimeoutAndBufferActor2])

    (1 to 25).foreach((i: Int) => {
      TimeUnit.SECONDS.sleep(1)
//      bufferActor1 ! i
      bufferActor2 ! i
    })
  }
}
