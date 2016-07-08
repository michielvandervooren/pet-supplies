package nl.sogeti.s2g2.reactive.controller.rest

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.server.{Directives, RouteResult}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import akka.util.Timeout

import scala.concurrent.duration._

/**
  * Created by voorenmi on 30-5-2016.
  */
class RestService(serviceName: String, version: String, host: String, port: Int) {

  import Directives._

  /* implicits for the bindAndHandle() */
  implicit val system = ActorSystem(serviceName)
  implicit val materializer = ActorMaterializer()

  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  def withResources(resources: List[Resource]): Unit = {

    val serviceRoutes = pathPrefix(serviceName / version) {
      resources map (_.routes) reduceLeft (_ ~ _)
    }

    /* The implicit conversion from Route=>Flow doesn't seem to work,
     * making it explicit does fix it and is a bit easier to follow as well
     */
    val routeFlow: Flow[HttpRequest, HttpResponse, Unit] = RouteResult.route2HandlerFlow(serviceRoutes)

    Http().bindAndHandle(handler = routeFlow, interface = host, port = port) map { binding =>
      println(s"${serviceName}/${version} REST api bound to ${binding.localAddress}")
    } recover { case ex =>
      println(s"${serviceName}/${version} REST api could not bind to $host:$port", ex.getMessage)
    }
  }
}

object RestService {
  def apply(serviceName: String, version: String, host: String, port: Int): RestService =
    new RestService(serviceName, version, host, port)
}

