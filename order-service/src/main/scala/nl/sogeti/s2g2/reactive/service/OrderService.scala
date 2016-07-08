package nl.sogeti.s2g2.reactive.service

import nl.sogeti.s2g2.reactive.controller.rest.OrderProtocol
import nl.sogeti.s2g2.reactive.dao.OrderDao
import nl.sogeti.s2g2.reactive.domain.OrderEntity

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by voorenmi on 3-7-2016.
  */
class OrderService(database: String, servers: Seq[String]) extends OrderDao(database, servers) with OrderProtocol {
  def createOrder(orderEntity: OrderEntity): Future[OrderCreated] = save(orderEntity)

  def deleteOrderEntity(id: String) = deleteById(id)

  def findAllOrders = findAll map extractOrders

  def findOrderById(id: String) = findById(id) map extractOrder

  def findOrdersByCustomer(customerId: String) = findByCustomerId(customerId) map extractOrders

  private def extractOrder(maybeOrder: Option[OrderEntity]) = maybeOrder match {
    case Some(orderEntity) => toOrder(orderEntity)
    case _ => OrderNotFound
  }

  private def extractOrders(orders: List[OrderEntity]) = orders match {
    case Nil => OrderNotFound
    case l:List[OrderEntity] => Orders(l.map {o => toOrder(o)})
  }
}
