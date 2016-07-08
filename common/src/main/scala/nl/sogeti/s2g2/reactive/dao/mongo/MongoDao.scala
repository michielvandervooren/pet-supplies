package nl.sogeti.s2g2.reactive.dao.mongo

import reactivemongo.api.MongoDriver
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by voorenmi on 30-5-2016.
  */
class MongoDao(val database: String, val servers: Seq[String]) {

  val driver = new MongoDriver
  val connection = driver.connection(servers)
  val db = connection(database)

  def queryById(id: String) = BSONDocument("_id" -> BSONObjectID(id))

  def emptyQuery = BSONDocument()
}
