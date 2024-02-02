package dev.restate.examples

import dev.restate.sdk.http.vertx.RestateHttpEndpointBuilder

object GreeterApp {
  def main(args: Array[String]): Unit = {
    RestateHttpEndpointBuilder
      .builder()
      .withService(new GreeterService())
      .buildAndListen()
  }
}
