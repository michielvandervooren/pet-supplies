package nl.sogeti.s2g2.reactive.dao

import nl.sogeti.s2g2.reactive.controller.rest.OrderProtocol
import nl.sogeti.s2g2.reactive.dao.mongo.MongoDao
import nl.sogeti.s2g2.reactive.domain.OrderEntity
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONObjectID, BSONString}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by voorenmi on 3-7-2016.
  */
class OrderDao(database: String, servers: Seq[String]) extends MongoDao(database, servers) with OrderProtocol {

  val collection = db[BSONCollection]("orders")

  def save(orderEntity: OrderEntity) = collection.save(orderEntity)
    .map(_ => OrderCreated(orderEntity.id.stringify))

  def findById(id: String) = collection.find(queryById(id)).one[OrderEntity]

  def findByCustomerId(id: String) = collection.find(queryByCustomerId(id)).cursor[OrderEntity].collect[List]()

  def findAll = collection.find(emptyQuery).cursor[OrderEntity].collect[List]()

  def deleteById(id: String) = collection.remove(queryById(id)).map(_ => OrderDeleted)

  private def queryByCustomerId(id: String) = BSONDocument("customerId" -> BSONString(id))
}
