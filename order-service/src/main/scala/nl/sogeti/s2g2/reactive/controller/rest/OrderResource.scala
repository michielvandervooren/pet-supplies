package nl.sogeti.s2g2.reactive.controller.rest

import akka.http.scaladsl.server._
import com.typesafe.config.ConfigFactory
import nl.sogeti.s2g2.reactive.domain.Order
import nl.sogeti.s2g2.reactive.service.OrderService

import scala.collection.JavaConverters._

/**
  * Created by voorenmi on 2-7-2016.
  */
object OrderResource extends Resource {
  val config = ConfigFactory.load()

  val database = config.getString("mongodb.database")
  val servers = config.getStringList("mongodb.servers").asScala

  val orderService = new OrderService(database, servers)

  def routes: Route = pathPrefix("orders") {
    pathEnd {
      post {
        entity(as[Order]) { order =>
          complete(orderService.createOrder(order))
        }
      } ~
      get {
        parameters('customer ?) { customer =>
          customer match {
            case Some(id) => complete(orderService.findOrdersByCustomer(id))
            case _ => complete(orderService.findAllOrders)
          }
        }
      }
    } ~
    path(Segment) { id =>
      delete {
        complete(orderService.deleteOrder(id))
      } ~
      get {
        complete(orderService.findOrderById(id))
      }
    }
  }
}
