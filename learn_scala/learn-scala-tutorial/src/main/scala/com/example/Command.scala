package com.example

class Command(key: String, func: => Long) {
  def execute(): Long = {
    println("Command.execute")
    println("key = " + key)
    println("func = " + func)
    func
  }

}
