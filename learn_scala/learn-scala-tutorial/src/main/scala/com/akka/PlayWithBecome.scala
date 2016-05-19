package com.akka

package PlayWithBecome {

  import java.util.concurrent.TimeUnit
  import akka.actor.Actor.Receive
  import akka.actor._
  import akka.event.LoggingReceive

  case object BrokerMsg

  case object ActorAMsg

  case object ActorADone

  case object ActorBMsg

  case object ActorBDone

  class BrokerActor(actorA: ActorRef, actorB: ActorRef) extends Actor with ActorLogging {
    override def receive = LoggingReceive {
      case BrokerMsg =>
        actorA ! ActorAMsg
        context.become(waitForActorA)
      case x =>
        println(s"BrokerActor.receive.Doesn't match case ${x}");
    }

    def waitForActorA = LoggingReceive {
      case ActorADone =>
        actorB ! ActorBMsg
        context.become(waitForActorB)
      case x =>
        println(s"BrokerActor.waitForActorA.Doesn't match case ${x}");
    }

    def waitForActorB = LoggingReceive {
      case ActorBDone =>
        context.become(receive)
      case x =>
        println(s"BrokerActor.waitForActorA.Doesn't match case ${x}");
    }

  }

  class ActorA extends Actor {
    override def receive: Actor.Receive = {
      case ActorAMsg =>
        TimeUnit.SECONDS.sleep(1)
        sender() ! ActorADone
      case x =>
        println(s"ActorA.receive.Doesn't match case ${x}");

    }
  }

  class ActorB extends Actor {
    override def receive: Actor.Receive = {
      case ActorBMsg =>
        TimeUnit.SECONDS.sleep(1)
        sender() ! ActorBDone
      case x =>
        println(s"ActorB.receive.Doesn't match case ${x}");
    }
  }

  object Main {
    def main(args: Array[String]) {

      val actorSystem: ActorSystem = ActorSystem("TestingBecome")
      val actorA: ActorRef = actorSystem.actorOf(Props[ActorA])
      val actorB: ActorRef = actorSystem.actorOf(Props[ActorB])

      val broker: ActorRef = actorSystem.actorOf(Props(classOf[BrokerActor], actorA, actorB))

      broker ! BrokerMsg
      broker ! BrokerMsg
    }
  }

}
