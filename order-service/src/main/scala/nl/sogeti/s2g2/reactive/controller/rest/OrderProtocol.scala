package nl.sogeti.s2g2.reactive.controller.rest

import java.util.Date

import nl.sogeti.s2g2.reactive.domain.{OrderEntity, OrderLineEntity}


/**
  * Created by voorenmi on 3-7-2016.
  */
trait OrderProtocol {

  case class OrderLine(productId: String, number: Int)

  case class Order(customerId: String, timestamp: Date, orderlines: List[OrderLine])

  case class Orders(orders: List[Order])

  case class OrderCreated(id: String)

  case object OrderDeleted

  case object OrderNotFound

  /* implicit conversions for dto->domain and domain->dto mapping */

  implicit def toOrder(orderEntity: OrderEntity) =
    Order(customerId = orderEntity.customerId,
      timestamp = orderEntity.timestamp,
      orderlines = orderEntity.orderlines.map(ol => OrderLine(ol.productId, ol.number)))

  implicit def toOrderEntity(order: Order) =
    OrderEntity(customerId = order.customerId,
      timestamp = order.timestamp,
      orderlines = order.orderlines.map((ol => OrderLineEntity(ol.productId, ol.number))))
}

object OrderProtocol extends OrderProtocol

