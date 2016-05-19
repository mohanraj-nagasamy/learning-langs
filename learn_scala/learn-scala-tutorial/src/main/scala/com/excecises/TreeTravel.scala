package com.excecises

object TreeTravel {

  //  trait Root

  case class Node(name: String, children: List[Node] = List.empty)

  def main(args: Array[String]): Unit = {
    println("TreeTravel.main")
//    val root = Node("Main", children = Node())
  }
}
