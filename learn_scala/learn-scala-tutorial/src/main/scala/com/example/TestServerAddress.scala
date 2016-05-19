package com.example

import java.net.InetSocketAddress

/**
 * Created by mohanraj.nagasamy on 10/12/15.
 */
object TestServerAddress {
  final def getAddress() =
    new InetSocketAddress("tt.btplug.dongxiguo.com", 1978)
}
