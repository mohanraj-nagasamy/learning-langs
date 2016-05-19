package com.akka.bank

import java.util.concurrent.TimeUnit

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

package ThreadedBankAccount {

  import java.util.concurrent.Executors

  import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

  class BankAccount(var balance: Int = 0) {

    def deposit(amount: Int): Int = this.synchronized {
      if (amount > 0) balance = balance + amount
      balance
    }

    def getBalance() = balance

    def withdraw(amount: Int): Int = this.synchronized {
      if (0 < amount && amount <= balance) {
        balance = balance - amount
        balance
      } else {
        //      throw new Error("insufficient funds")
        balance
      }
    }

  }

  object BankAccount {
    def transfer(from: BankAccount, to: BankAccount, amount: Int): (Int, Int) = {
      //      scala.concurrent.stm.atomic {
      //
      //      }
      from.synchronized {
        to.synchronized {
          from.withdraw(amount)
          to.deposit(amount)
        }
      }
      (from.getBalance(), to.getBalance())
    }

  }

  object Main {
    //    implicit val fromExecutor: ExecutionContextExecutor = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(50))

    private val numberofTasks: Int = 10000
    private val deposit: Int = 1
    private val withdraw: Int = 1


    def doDeposits(mohanAccount: BankAccount): Future[List[Int]] = {
      Future.sequence {
        List.fill(numberofTasks)(Future {
          TimeUnit.MICROSECONDS.sleep(10)
          mohanAccount.deposit(deposit)
        })
      }
    }

    def doWithdraw(mohanAccount: BankAccount): Future[List[Int]] = {
      Future.sequence {
        List.fill(numberofTasks)(Future {
          TimeUnit.MICROSECONDS.sleep(10)
          mohanAccount.withdraw(withdraw)
        })
      }
    }

    def accountTrasferTest() {
      val initBalance: Int = numberofTasks
      val from = new BankAccount(initBalance)
      val to = new BankAccount(initBalance)
      println("from.getBalance() = " + from.getBalance())
      println("to.getBalance() = " + to.getBalance())

      val fromAcToToAc: Future[List[(Int, Int)]] = Future.sequence {
        List.fill(numberofTasks) {
          Future {
            TimeUnit.MICROSECONDS.sleep(10)
            BankAccount.transfer(from, to, 1)
          }
        }
      }

      val toAcToFromAc: Future[List[(Int, Int)]] = Future.sequence {
        List.fill(numberofTasks) {
          Future {
            TimeUnit.MICROSECONDS.sleep(10)
            BankAccount.transfer(to, from, 1)
          }
        }
      }

      val result1: List[(Int, Int)] = Await.result(fromAcToToAc, Duration.Inf)
      println("fromAcToToAc = " + result1)
      val result2: List[(Int, Int)] = Await.result(toAcToFromAc, Duration.Inf)
      println("toAcToFromAc = " + result2)

      println("from.getBalance() = " + from.getBalance())
      println("to.getBalance() = " + to.getBalance())
    }

    def main(args: Array[String]) {

      //      val mohanAccount = new BankAccount
      //      val depositSequence: Future[List[Int]] = doDeposits(mohanAccount)
      //      val withdrawSequence: Future[List[Int]] = doWithdraw(mohanAccount)

      //      val deposits: List[Int] = Await.result(depositSequence, Duration.Inf)
      //      println("deposits = " + deposits)
      //      val withdraws: List[Int] = Await.result(withdrawSequence, Duration.Inf)
      //      println("withdraws = " + withdraws)
      //      println("mohanAccount.getBalance() = " + mohanAccount.getBalance())

      accountTrasferTest()

    }

  }

}