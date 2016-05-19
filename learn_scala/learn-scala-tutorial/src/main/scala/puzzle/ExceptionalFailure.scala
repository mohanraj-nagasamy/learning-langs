package puzzle

import scala.concurrent.Future
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global

object ExceptionalFailure {
  def main(args: Array[String]) {
    val f: Future[String] = Future {
      throw new Error("fatal!")
    } recoverWith {
      case err: Error => Future.successful("Ignoring error: " + err.getMessage)
//      case x =>
//        print("x" + x)
//        Future.successful("Object")
    }


    f onComplete {
      case Success(res) => println("Yay: " + res)
      case Failure(e) => println("Oops: " + e.getMessage)
    }


    /**/
  }
}
