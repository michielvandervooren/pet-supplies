package nl.sogeti.s2g2.reactive.dao

import nl.sogeti.s2g2.reactive.dao.mongo.MongoDao
import nl.sogeti.s2g2.reactive.domain.Category
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONString}

import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by voorenmi on 12-7-2016.
  */
class CategoryDao(database: String, servers: Seq[String]) extends MongoDao(database, servers) {

  val categoryCollection = db[BSONCollection]("categories")

  def insert(category: Category) = categoryCollection.save(category)
    .map(_ => category)

  def update(name: String, category: Category) = {
    val modifier = BSONDocument("$set" -> BSONDocument("description" -> BSONString(category.description)))
    categoryCollection.update(queryByName(name), modifier)
      .map(_ => category)
  }

  def findByName(name: String) = categoryCollection.find(queryByName(name)).one[Category]

  def findAll = categoryCollection.find(emptyQuery).cursor[Category].collect[List]()

  def deleteByName(name: String) = categoryCollection.remove(queryByName(name)).map(_ => name)

  private def queryByName(name: String) = BSONDocument("_id" -> BSONString(name))
}
