package dev.restate.examples

import dev.restate.examples.generated.greeter.{
  GreetRequest,
  GreetResponse,
  GreeterGrpc
}
import dev.restate.sdk.common.{CoreSerdes, StateKey}
import dev.restate.sdk.RestateService
import io.grpc.ServerServiceDefinition

import scala.concurrent.{ExecutionContext, Future}

class GreeterService extends GreeterGrpc.Greeter with RestateService {
  override def greet(request: GreetRequest): Future[GreetResponse] = {
    val ctx = this.restateContext();
    val count = ctx.get(GreeterService.COUNT).orElse(1);

    // conversion needed to map Scala Int to Java Integer
    ctx.set(GreeterService.COUNT, Integer.valueOf(count + 1));

    Future.successful(
      GreetResponse.defaultInstance.withMessage(
        s"Hello ${request.name} for the $count time!"
      )
    )
  }

  override def bindService(): ServerServiceDefinition =
    GreeterGrpc.bindService(this, ExecutionContext.global)
}

object GreeterService {
  private val COUNT: StateKey[Integer] = StateKey.of("count", CoreSerdes.INT);
}
