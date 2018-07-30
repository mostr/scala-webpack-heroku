package sample.infrastructure

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives
import akka.stream.Materializer
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

trait Routes extends Directives {

  private lazy val healthCheck = path("status") {
    get {
      complete(StatusCodes.OK)
    }
  }

  lazy val routes = pathPrefix("api") {
    healthCheck
  } ~ getFromResourceDirectory("webapp") ~ getFromResource("webapp/index.html")

}

class HttpService(config: HttpConfig)(implicit system: ActorSystem, materializer: Materializer, ec: ExecutionContext)
    extends Routes
    with LazyLogging {

  def start(): Future[Http.ServerBinding] =
    Http().bindAndHandle(routes, config.host, config.port).andThen {
      case Success(_) => logger.info(s"HTTP API ready at ${config.host}:${config.port}")
      case Failure(e) => logger.error("Unable to start HTTP API", e)
    }
}
