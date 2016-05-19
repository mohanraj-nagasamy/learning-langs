package com.selftypes

import java.time.LocalDateTime

object SelfTypeExample {
  trait CRUDEntity[E <: CRUDEntity[E]] { self: E =>
    def create(entityData: String): E = ???
    def read(id: String): Option[E] = ???
    def update(f: E => E): E = ???
    def delete(id: String): Unit = ???
  }
  case class Animal(name: String) extends CRUDEntity[Animal]
  case class Apple(name: String, price: Double) extends CRUDEntity[Apple]
  case class Bird(name: String, birthday: LocalDateTime) extends CRUDEntity[Bird]

  def main(args: Array[String]) {
    println("SelfTypeExample.main")
    val apple: Apple = Apple("My apple", 232.34)
    println("apple = " + apple)
    println("bir = " + Bird("My apple", LocalDateTime.now()))
  }
}
