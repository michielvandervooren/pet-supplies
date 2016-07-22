package nl.sogeti.s2g2.reactive.service

import nl.sogeti.s2g2.reactive.dao.OrderDao
import nl.sogeti.s2g2.reactive.domain.Order

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by voorenmi on 3-7-2016.
  */
class OrderService(database: String, servers: Seq[String]) extends OrderDao(database, servers) {

  def createOrder(orderEntity: Order): Future[Order] = save(orderEntity)

  def deleteOrder(id: String): Future[String] = deleteById(id)

  def findAllOrders: Future[List[Order]] = findAll

  def findOrderById(id: String): Future[Option[Order]] = findById(id)

  def findOrdersByCustomer(customerId: String): Future[List[Order]] = findByCustomerId(customerId)

}
