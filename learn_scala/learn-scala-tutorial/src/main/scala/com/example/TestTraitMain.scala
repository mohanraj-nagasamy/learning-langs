package com.example

object TestTraitMain {

  trait Language {
    def lang(): Unit
  }

  trait Functional extends Language {
    override def lang() {
      println("Functional")
    }
  }

  trait ObjectOriented extends Language {
    override def lang() {
      println("ObjectOriented")
    }
  }

  class Scala extends Functional with ObjectOriented

  //  class Scala extends ObjectOriented with Functional
  //---------------------------
  trait Lambda {
    val l = "Lambda"
  }

  trait Calculus {
    this: Lambda =>
    val c = "Calculus"
    def lc = l + c
  }

  trait Turing {
    this: Calculus =>
    val t = "Turing"
    def lct = lc + t // compilation error(cannot access l)
  }

  class FunLan extends Calculus with Lambda

  def main(args: Array[String]) {
    new Scala().lang()
    val universe = new Turing with Calculus with Lambda
    println(universe.lct)
  }
}
