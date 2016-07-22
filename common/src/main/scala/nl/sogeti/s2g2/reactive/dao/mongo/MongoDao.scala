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

  def queryById(id: String) = try {
    // we don't want malformed BSONObjectID strings to result in a 500
    // so we catch the IllegalArgumentException
    BSONDocument("_id" -> BSONObjectID(id))
  } catch {
      // effectively results in a 404
      case iae:IllegalArgumentException => BSONDocument("_id" -> BSONObjectID("00" * 12))
      case t: Throwable => throw t
  }

  def emptyQuery = BSONDocument()
}
