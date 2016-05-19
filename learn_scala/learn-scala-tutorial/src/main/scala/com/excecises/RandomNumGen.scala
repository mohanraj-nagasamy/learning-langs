package com.excecises

object RandomNumGen {

  trait Gen[+T] {
    self =>

    def generate: T

    def map[S](f: T => S): Gen[S] = new Gen[S] {
      override def generate: S = f(self.generate)
    }

    def flatMap[S](f: T => Gen[S]): Gen[S] = new Gen[S] {
      override def generate: S = f(self.generate).generate
    }
  }

  def main(args: Array[String]) {

    val intGen = new Gen[Int] {
      val random = new java.util.Random

      override def generate: Int = {
        random.nextInt
      }
    }

    val pairInt = new Gen[(Int, Int)] {
      val random = new java.util.Random

      override def generate: (Int, Int) = (random.nextInt(), random.nextInt())
    }

    println("intGen.generate = " + intGen.generate)
    println("pairInt.generate = " + pairInt.generate)

    val booleanGen = for (x <- intGen) yield x > 0

    val pairs: Gen[(Int, Int)] = for {
      x <- intGen
      y <- intGen
    } yield (x, y)

    println("booleanGen = " + booleanGen.generate)
    println("pairs.generate = " + pairs.generate)

    def single[T](t: T) = new Gen[T] {
      override def generate: T = t
    }

    def choose(lo: Int, hi: Int): Gen[Int] = for (x <- intGen) yield lo + x % (hi - lo)

    def oneOf[T](xs: T*): Gen[T] = for (idx <- choose(0, xs.length)) yield xs(idx)

    val oneOf1 = oneOf("One", "Two", "Three", "Four")
    println("oneOf1 = " + oneOf1.generate)

    def lists: Gen[List[Int]] = for {
      isEmpty <- booleanGen
      list <- if (isEmpty) emptyLists else nonEmptyLists
    } yield list

    def emptyLists = single(Nil)

    def nonEmptyLists = for {
      head <- intGen
      tail <- lists
    } yield head :: tail

    println("lists.generate = " + lists.generate)
  }

}
