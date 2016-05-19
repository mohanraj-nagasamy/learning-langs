package com.akka.stm

import scala.concurrent.stm
import scala.concurrent.stm.Ref

object STMMain {

  case class Account(acNo: String, balance: Int) {
    def debit(amount: Int) = this.copy(balance = balance - amount)

    def credit(amount: Int) = this.copy(balance = balance + amount)
  }

  def transfer(fromAcNo: String, toAcNo: String, amount: Int): Unit = {
    stm.atomic { implicit t =>
      val accounts: Ref[Map[String, Account]] = accountRefs
      val (from, to) = (accounts().get(fromAcNo).get, accounts().get(toAcNo).get)

      if (from.balance > amount) {
//        accounts = accounts + (to.acNo -> to.credit(amount))

      }


    }
  }

  val accountRefs = Ref(Map("from" -> Account("from", 100), "to" -> Account("to", 200)))

}
