package sample

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging

object SampleApp extends App with LazyLogging {
  val app = new Module {
    override implicit val actorSystem: ActorSystem = ActorSystem()
    override implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()
  }

  app.httpService.start()

}
