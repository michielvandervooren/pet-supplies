package nl.sogeti.s2g2.reactive.domain

import java.util.Date

import reactivemongo.bson.{BSONDateTime, BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}

/**
  * Created by voorenmi on 3-7-2016.
  */
case class Order(id: String = BSONObjectID.generate.stringify,
                 customerId: String,
                 timestamp: Date,
                 orderlines: List[OrderLine])

case class OrderLine(id: String = BSONObjectID.generate.stringify,
                     productId: String,
                     quantity: Int)

object OrderLine {

  implicit object OrderLineReader extends BSONDocumentReader[OrderLine] {
    def read(doc: BSONDocument): OrderLine =
      OrderLine(
        id = doc.getAs[BSONObjectID]("_id").get.stringify,
        productId = doc.getAs[String]("productId").get,
        quantity = doc.getAs[Int]("quantity") .get
      )
  }

  implicit object OrderLineWriter extends BSONDocumentWriter[OrderLine] {
    def write(orderLine: OrderLine): BSONDocument =
      BSONDocument(
        "_id" -> BSONObjectID(orderLine.id),
        "productId" -> orderLine.productId,
        "quantity" -> orderLine.quantity
      )
  }
}

object Order {

  implicit object OrderReader extends BSONDocumentReader[Order] {

    def read(doc: BSONDocument): Order =
      Order(
        id = doc.getAs[BSONObjectID]("_id").get.stringify,
        customerId = doc.getAs[String]("customerId").get,
        timestamp = doc.getAs[BSONDateTime]("timestamp").map(dt => new Date(dt.value)).get,
        orderlines = doc.getAs[List[OrderLine]]("orderlines").toList.flatten
      )
  }

  implicit object OrderWriter extends BSONDocumentWriter[Order] {
    def write(order: Order): BSONDocument =
      BSONDocument(
        "_id" -> BSONObjectID(order.id),
        "customerId" -> order.customerId,
        "timestamp" -> BSONDateTime(order.timestamp.getTime),
        "orderlines" -> order.orderlines
      )
  }
}