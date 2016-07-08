package nl.sogeti.s2g2.reactive.domain

import java.util.Date

import reactivemongo.bson.{BSONDateTime, BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}

/**
  * Created by voorenmi on 3-7-2016.
  */
case class OrderEntity(id: BSONObjectID = BSONObjectID.generate,
                       customerId: String,
                       timestamp: Date,
                       orderlines: List[OrderLineEntity])

case class OrderLineEntity(productId: String, number: Int)

object OrderLineEntity {

  implicit object OrderLineReader extends BSONDocumentReader[OrderLineEntity] {
    def read(doc: BSONDocument): OrderLineEntity =
      OrderLineEntity(
        productId = doc.getAs[String]("productId").get,
        number = doc.getAs[Int]("number") .get
      )
  }

  implicit object OrderLineWriter extends BSONDocumentWriter[OrderLineEntity] {
    def write(orderLine: OrderLineEntity): BSONDocument =
      BSONDocument(
        "productId" -> orderLine.productId,
        "number" -> orderLine.number
      )
  }
}

object OrderEntity {

  implicit object OrderEntityBSONReader extends BSONDocumentReader[OrderEntity] {

    def read(doc: BSONDocument): OrderEntity =
      OrderEntity(
        id = doc.getAs[BSONObjectID]("_id").get,
        customerId = doc.getAs[String]("customerId").get,
        timestamp = doc.getAs[BSONDateTime]("timestamp").map(dt => new Date(dt.value)).get,
        orderlines = doc.getAs[List[OrderLineEntity]]("orderlines").toList.flatten
      )
  }

  implicit object OrderEntityBSONWriter extends BSONDocumentWriter[OrderEntity] {
    def write(orderEntity: OrderEntity): BSONDocument =
      BSONDocument(
        "_id" -> orderEntity.id,
        "customerId" -> orderEntity.customerId,
        "timestamp" -> BSONDateTime(orderEntity.timestamp.getTime),
        "orderlines" -> orderEntity.orderlines
      )
  }
}