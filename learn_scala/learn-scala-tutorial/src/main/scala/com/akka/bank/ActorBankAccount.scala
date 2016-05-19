package com.akka.bank

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.duration.Duration._

package ActorBankAccount {

  import java.util.concurrent.TimeUnit

  import akka.actor._
  import akka.event.LoggingReceive
  import akka.util.Timeout

  import scala.concurrent.duration.{DurationLong, Duration}
  import scala.concurrent.{Await, Future}

  object BankAccount {

    case class Deposit(amount: BigInt) {
      require(amount > 0)
    }

    case class Withdraw(amount: BigInt) {
      require(amount > 0)
    }

    case object Done

    case object Balance

    case object Failed

  }

  //Actor that receives messages to perform actions of a bank account
  class BankAccount(var balance: BigInt = BigInt(0)) extends Actor with ActorLogging {

    import BankAccount._

    def receive = LoggingReceive {
      //Deposit messages add amount to balance state
      case Deposit(amount) =>
        log.info("Deposit.amount = " + amount)
        balance += amount
        log.info("Deposit.balance = " + balance)
        sender ! Done

      //Withdraw messages subtract amount from balance state
      case Withdraw(amount) if amount <= balance =>
        log.info("Withdraw.amount = " + amount)
        balance -= amount
        log.info("Withdraw.balance = " + balance)
        sender ! Done

      case Balance =>
        log.info("Balance.balance = " + balance)
        sender ! balance

      //Any other message would return a failure to the sender
      case _ => sender ! Failed
    }

  }

  object Main {
    private val numberofTasks: Int = 1
    private val deposit: Int = 10
    private val withdraw: Int = 5

    def doDeposits(mohanAccountRef: ActorRef) = {
      Future.sequence {
        List.fill(numberofTasks)(Future {
          TimeUnit.MICROSECONDS.sleep(10)
          mohanAccountRef ! BankAccount.Deposit(deposit)
        })
      }
    }

    def doWithdraw(mohanAccountRef: ActorRef) = {
      Future.sequence {
        List.fill(numberofTasks)(Future {
          TimeUnit.MICROSECONDS.sleep(10)
          mohanAccountRef ! BankAccount.Withdraw(withdraw)
        })
      }
    }

    def main(args: Array[String]) {
      val actorSystem: ActorSystem = ActorSystem("mohanAccount")
      val mohanAccountRef: ActorRef = actorSystem.actorOf(Props(classOf[BankAccount], BigInt(0)))

      val depositSequence = doDeposits(mohanAccountRef)
      val withdrawSequence = doWithdraw(mohanAccountRef)

      val deposits = Await.result(depositSequence, Duration.Inf)
      println("deposits = " + deposits)

      val withdraws = Await.result(withdrawSequence, Duration.Inf)
      println("withdraws = " + withdraws)
      implicit val timeout = Timeout(5 seconds)
      akka.pattern.ask(mohanAccountRef, BankAccount.Balance).map {
        case balance: BigInt =>
          println("balance = " + balance)
        case x =>
          println("x = " + x)
      }
    }
  }

  object WireTransfer {

    case class Transfer(from: ActorRef, to: ActorRef, amount: BigInt)

    case object Done

    case object Failed

  }

  //actor implementing the actions of a wire transfer between two bank account actors
  class WireTransfer extends Actor {

    import WireTransfer._

    def receive = LoggingReceive {
      //If Transfer message is received, send withdraw message to 'from' and wait for reply
      case Transfer(from, to, amount) =>
        from ! BankAccount.Withdraw(amount)
        context.become(awaitFrom(to, amount, sender), false)
    }

    //If Withdraw was successful, send deposit to other bank account actor, or else give them a failure message
    def awaitFrom(to: ActorRef, amount: BigInt, customer: ActorRef): Receive = LoggingReceive {
      case BankAccount.Done =>
        to ! BankAccount.Deposit(amount)
        context.become(awaitTo(customer), false)
      case BankAccount.Failed =>
        customer ! Failed
        context.stop(self)
    }

    //If deposit was successful, send 'Done' to original actor that sent Transfer message
    def awaitTo(customer: ActorRef): Receive = LoggingReceive {
      case BankAccount.Done =>
      //        customer ! Done
      //        context.stop(self)
      context.become(receive)
    }
  }

  object WireTransferMain {
    val initBalance = BigInt(1000)

    def main(args: Array[String]) {
      val actorSystem: ActorSystem = ActorSystem("WireTransfer")
      val transaction = actorSystem.actorOf(Props[WireTransfer], "transfer")

      val from: ActorRef = actorSystem.actorOf(Props(classOf[BankAccount], initBalance), "from-account")
      val to: ActorRef = actorSystem.actorOf(Props(classOf[BankAccount], initBalance), "to-account")

      implicit val timeout = Timeout(FiniteDuration(50, "sec"))
      akka.pattern.ask(from, BankAccount.Balance).map {
        case balance: BigInt =>
          println("from.getBalance() = " + balance)
      }
      akka.pattern.ask(to, BankAccount.Balance).map {
        case balance: BigInt =>
          println("to.getBalance() = " + balance)
      }
      TimeUnit.MICROSECONDS.sleep(1000)
      println("------------")
      val fromAcToToAc = Future.sequence {
        List.fill(initBalance.toInt) {
          Future {
            TimeUnit.MICROSECONDS.sleep(10)
            transaction ! WireTransfer.Transfer(from, to, 1)
            println("From-TO-1");
          }
        }
      }

      val toAcToFromAc = Future.sequence {
        List.fill(initBalance.toInt) {
          Future {
            TimeUnit.MICROSECONDS.sleep(10)
            transaction ! WireTransfer.Transfer(to, from, 1)
            println("To-FROM-1");
          }
        }
      }
      val result1 = Await.result(fromAcToToAc, Duration.Inf)
      println("result1 = " + result1)
      val result2 = Await.result(toAcToFromAc, Duration.Inf)
      println("result2 = " + result2)

      akka.pattern.ask(from, BankAccount.Balance).map {
        case balance: BigInt =>
          println("from.getBalance() = " + balance)
      }
      akka.pattern.ask(to, BankAccount.Balance).map {
        case balance: BigInt =>
          println("to.getBalance() = " + balance)
      }
    }
  }

}
