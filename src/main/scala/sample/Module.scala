package sample

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import sample.infrastructure.{HttpConfig, HttpService}

import scala.concurrent.ExecutionContext

trait Module {
  implicit val actorSystem: ActorSystem
  implicit val actorMaterializer: ActorMaterializer
  implicit val ec = ExecutionContext.global

  val config = ConfigFactory.load()
  lazy val httpService = new HttpService(HttpConfig(config))
}
