package com.akka

import akka.actor.{Props, ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestProbe, TestKit}
import com.akka.TestSimpleActor.{Decrement, Increment, GetCount, Counter}
import org.scalatest._

class TestSimpleActorSpec extends TestKit(ActorSystem("test-counter"))
  with FlatSpecLike
  with ImplicitSender
  with BeforeAndAfterAll
  with MustMatchers {


  it should "return count" in {
    val sender = TestProbe()
    val counter: ActorRef = system.actorOf(Props[Counter])

    sender.send(counter, GetCount)
    sender.expectMsg(0)
    //    val count: Int = sender.expectMsgType[Int]
    //    println("sender.expectMsg(0) = " + count)

  }

  it should "Inc the count" in {

    val counter: ActorRef = system.actorOf(Props[Counter])
    counter ! Increment
    expectNoMsg()
  }

  it should "Dec the count" in {

    val counter: ActorRef = system.actorOf(Props[Counter])
    counter ! Decrement
    expectNoMsg()
  }

  it should "Get the count" in {
    val counter: ActorRef = system.actorOf(Props[Counter])
    counter ! Increment
    counter ! Increment

    counter ! Decrement

    counter ! Increment

    counter ! GetCount

    expectMsg(2)
  }

  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }
}
