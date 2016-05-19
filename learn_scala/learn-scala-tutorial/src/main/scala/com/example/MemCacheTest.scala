package com.example

import java.lang.RuntimeException
import java.net.{Socket, SocketAddress, InetAddress, InetSocketAddress}
import java.util.concurrent.{ExecutorService, Executors, TimeUnit}

import net.spy.memcached._
import net.spy.memcached.auth.{PlainCallbackHandler, AuthDescriptor}

import scala.StringBuilder
import scala.concurrent.duration.Duration
import scala.util.{Random, Try, Failure, Success}

/**
 * Created by mohanraj.nagasamy on 10/5/15.
 */
object MemCacheTest {

  def main(args: Array[String]) {
    println("MemCacheTest$.main")

    //    loadTestMemCached
        singleMemCachedTest

    //    isConnected

//        testMemCachedClient

    //    testInLoop

  }

  def loadTestMemCached: Unit = {
    val build: ConnectionFactory = new DefaultConnectionFactory() {
      override def getOperationTimeout: Long = 1000

      //      override def getFailureMode: FailureMode = FailureMode.Retry
    }

    val client: MemcachedClient = new MemcachedClient(build, AddrUtil.getAddresses("localhost:11212"))
    val pool: ExecutorService = Executors.newFixedThreadPool(100)


    for (i <- 0 to 100) {
      pool.execute(new Runnable {
        override def run(): Unit = {
          val name: String = Thread.currentThread.getName()

          while (true) {
            TimeUnit.MILLISECONDS.sleep(100)
            try {
              val sb: StringBuilder = new StringBuilder()
              for (i <- (0 to 100000)) {
                sb.append(Random.nextString(1))
              }
              var start: Long = System.currentTimeMillis()


              client.set("TEST_JAVA_VALUE", 0, sb.toString())
              var end: Long = System.currentTimeMillis() - start
              if (end >= 500) System.out.println(name + " Set took " + end + "ms.")

              start = System.currentTimeMillis()
              val value: String = client.get("TEST_JAVA_VALUE").asInstanceOf[String]
              //        println("value = " + value)

              end = System.currentTimeMillis() - start
              if (end >= 500) System.out.println(name + " Retrieve took " + end + "ms.")
            }
            catch {
              case ex: Exception =>
                println(name + " client.getStats = " + client.getStats)
                ex.printStackTrace()
            }
          }
        }
      })
    }

    println("pool = " + pool)
    pool.shutdown()
    //    Thread.currentThread().join()
    println("pool.isTerminated = " + pool.isTerminated)
  }

  def singleMemCachedTest: Unit = {
    val build: ConnectionFactory = new DefaultConnectionFactory() {
      override def getOperationTimeout: Long = 1000


      //      override def getFailureMode: FailureMode = FailureMode.Retry
      override def getAuthDescriptor: AuthDescriptor = new AuthDescriptor(Array[String]("PLAIN"), new PlainCallbackHandler("VBDMWQTN", "IGRSQCNV"))
    }


    val client: MemcachedClient = new MemcachedClient(build, AddrUtil.getAddresses("10.100.107.156:30038"))

    try {
      val sb: StringBuilder = new StringBuilder()
      for (i <- (0 to 10)) {
        sb.append(Random.nextString(1))
      }
      var start: Long = System.currentTimeMillis()

      client.set("TEST_JAVA_VALUE", 0, sb.toString())
      System.out.println("Set took " + (System.currentTimeMillis() - start) + "ms.")

      start = System.currentTimeMillis()
      val value: String = client.get("TEST_JAVA_VALUE").asInstanceOf[String]

      System.out.println("Retrieve took " + (System.currentTimeMillis() - start) + "ms.")
      println("client.getStats = " + client.getStats)
    } finally {
      client.shutdown()
    }
  }

  def getOrElse[T](memCachierClient: MemcachedClient, cacheKey: String, expiration: Int = 1)(func: => T): T = {


    def callUserFn: T = {
      val result = func
      memCachierClient.set(cacheKey, expiration, result)
      result
    }

    Try(memCachierClient.get(cacheKey)) match {
      case Success(cachedValue) =>
        if (cachedValue == null) {
          callUserFn
        }
        else {
          cachedValue.asInstanceOf[T]
        }
      case Failure(ex) =>
        ex.printStackTrace()
        callUserFn
    }

  }

  def isConnected: Unit = {
    val address: InetSocketAddress = new InetSocketAddress("localhost", 11211)
    println("address.getAddress = " + address.getAddress)
    println("address.getAddress.isReachable(TimeUnit.SECONDS.toMillis(1)) = " + address.getAddress.isReachable(TimeUnit.SECONDS.toMillis(1).toInt))
    val socket: Socket = new Socket()
    socket.connect(address)
    println("socket.isConnected = " + socket.isConnected)
  }

  def testMemCachedClient: Unit = {
    val c: MemcachedClient = new MemcachedClient(new InetSocketAddress("69701.56e942.us-east-4.heroku.prod.memcachier.com", 11211))
    //    val c: MemcachedClient = new MemcachedClient(new InetSocketAddress("localhost", 11211))
    //    val value: Object = c.get("someKey")
    val list: java.util.ArrayList[SocketAddress] = c.getAvailableServers.asInstanceOf[java.util.ArrayList[SocketAddress]]
    println("list = " + list)
    println("c.getAvailableServers.isEmpty = " + c.getAvailableServers.isEmpty)
    //    list.get(0)
    //    println("value = " + value)
    val reachable: Boolean = InetAddress.getByName("69701.56e942.us-east-4.heroku.prod.memcachier.com").isReachable(1000)

    println("reachable = " + reachable)


    //    val addresses: java.util.List[InetSocketAddress] = AddrUtil.getAddresses("69701.56e942.us-east-4.heroku.prod.memcachier.com:11211")
    val addresses: java.util.List[InetSocketAddress] = AddrUtil.getAddresses("localhost:11211 localhost:11212")

    println("addresses.get(1).getHostName = " + addresses.get(1).getHostName)

    val client: MemcachedClient = new MemcachedClient(AddrUtil.getAddresses("localhost:11211 localhost:11212"))
    client.getAvailableServers
    client.getUnavailableServers
    val address: InetSocketAddress = addresses.get(0)
    println("address = " + address.getAddress.isReachable(1000))

    val builder: ConnectionFactoryBuilder = new ConnectionFactoryBuilder()
      .setOpTimeout(1000)
      .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
      .setFailureMode(FailureMode.Redistribute)
  }

  def testInLoop: Unit = {
    println("IN loopppppp")
    for (i <- 1 to 1000) {
      val client: MemcachedClient = new MemcachedClient(AddrUtil.getAddresses("localhost:11211 localhost:11212"))
      println("client.getAvailableServers = " + client.getAvailableServers)
      TimeUnit.MILLISECONDS.sleep(100)
    }
  }
}
