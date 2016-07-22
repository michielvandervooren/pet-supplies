package nl.sogeti.s2g2.reactive.dao

import nl.sogeti.s2g2.reactive.dao.mongo.MongoDao
import nl.sogeti.s2g2.reactive.domain.Order
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONString}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by voorenmi on 3-7-2016.
  */
class OrderDao(database: String, servers: Seq[String]) extends MongoDao(database, servers) {

  val collection = db[BSONCollection]("orders")

  def save(orderEntity: Order) = collection.save(orderEntity)
    .map(_ => {orderEntity})

  def findById(id: String) = collection.find(queryById(id)).one[Order]

  def findByCustomerId(id: String) = collection.find(queryByCustomerId(id)).cursor[Order].collect[List]()

  def findAll = collection.find(emptyQuery).cursor[Order].collect[List]()

  def deleteById(id: String) = collection.remove(queryById(id)).map(_ => id)

  private def queryByCustomerId(id: String) = BSONDocument("customerId" -> BSONString(id))
}
