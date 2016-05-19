package com.excecises

object NodeChains {

  case class Node[T](value: T, var next: Node[T] = null)

  class TestLinkedList[T] {
    private var head: Node[T] = null
    private var tail: Node[T] = null
    var count = 0

    def addFirst(element: T): Unit = {
      if (head == null) {
        head = Node(element)
        tail = Node(element)
      } else {
        val oldHead = head
        head = Node(element)
        head.next = oldHead
      }
      count += 1
    }

    def addLast(element: T): Unit = {
      val node: Node[T] = Node(element)
      if (tail == null) {
        head = node
        tail = head
      } else {
        tail.next = node
        tail = node
      }
      count += 1
    }

    def removeFirst: T = {
      val node: Node[T] = head.next
      val removedValue: T = head.value
      head = node
      removedValue
    }

    def removeLast: T = {
      var currentElemenet = head
      println("tail = " + tail)
      while (currentElemenet.next != tail) {
        currentElemenet = currentElemenet.next
      }
      val oldTail = currentElemenet.next
      currentElemenet.next = null
      tail = currentElemenet
      oldTail.value
    }

    def printNodes(): Unit = {

      def itr(node: Node[T]): Unit = {
        node match {
          case n: Node[T] =>
            println("Value = " + n.value)
            itr(n.next)
          case _ =>
            println("END")
        }
      }
      itr(head)
      println("head = " + head)
    }
  }

  def main(args: Array[String]) {
    //    val n1: Node[Int] = Node(3)
    //    val n5: Node[Int] = Node(5)
    //    n1.next = n5
    //    val n7: Node[Int] = Node(7)
    //    n5.next = n7

    val linkedList: TestLinkedList[Int] = new TestLinkedList[Int]
    linkedList.addLast(1)
    linkedList.addLast(2)
    linkedList.addLast(3)
    linkedList.addLast(4)
    linkedList.addLast(5)

    linkedList.printNodes
    println("linkedList.count = " + linkedList.count)

    //    println("------------------------------")
    //    println("linkedList.removeFirst = " + linkedList.removeFirst)
    //    println("linkedList.removeFirst = " + linkedList.removeFirst)
    //    linkedList.printNodes
    //    println("linkedList.count = " + linkedList.count)

    println("------------------------------")
    println("linkedList.removeLast = " + linkedList.removeLast)
    println("linkedList.removeLast = " + linkedList.removeLast)
    linkedList.printNodes
    println("linkedList.count = " + linkedList.count)

  }

}
