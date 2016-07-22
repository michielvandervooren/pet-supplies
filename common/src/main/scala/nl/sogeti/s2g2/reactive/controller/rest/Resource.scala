package nl.sogeti.s2g2.reactive.controller.rest

import akka.http.scaladsl.marshalling.{ToResponseMarshallable, ToResponseMarshaller}
import akka.http.scaladsl.unmarshalling.{ Unmarshaller, FromRequestUnmarshaller }
import akka.http.scaladsl.server._
import nl.sogeti.s2g2.reactive.controller.rest.json.JsonSupport

import scala.concurrent.Future

/**
  * Created by voorenmi on 30-5-2016.
  */
trait Resource extends Directives with JsonSupport {
  def routes: Route
  implicit def resourceToList(resource: Resource): List[Resource] = List(resource)

  def complete[T: ToResponseMarshaller](resource: Future[Option[T]]): Route =
    onSuccess(resource) {
      case Some(t) => complete(ToResponseMarshallable(t))
      case None => complete(404, None)
    }

//  type Id = String
//  type All[R] = () => Future[List[R]]
//  type Get[R] = (Id) => Future[Option[R]]
//  type Post[R] = (R) => Future[R]
//  type Put[R] = (Id, R) => Future[R]
//  type Delete[R] = (Id) => Future[Id]
//
//  def crudRoutesFor[R](all: All[R])(create: Post[R])(read: Get[R])(update: Put[R])(remove: Delete[R]): Route = {
//    pathEnd {
//      post {
//        entity(as[R]) { r =>
//          complete(create(r))
//        }
//      } ~
//      get {
//        complete(all)
//      }
//    } ~
//    path(Segment) { id =>
//      delete {
//        complete(remove(id))
//      } ~
//      get {
//        complete(read(id))
//      } ~
//      put {
//        entity(as[R]) { r =>
//          complete(update(id, r))
//        }
//      }
//    }
//  }
}
