package nl.sogeti.s2g2.reactive.dao

import nl.sogeti.s2g2.reactive.dao.mongo.MongoDao
import nl.sogeti.s2g2.reactive.domain.Product
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONString}

import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by voorenmi on 12-7-2016.
  */
class ProductDao(database: String, servers: Seq[String]) extends MongoDao(database, servers) {

  val productCollection = db[BSONCollection]("products")

  def insert(product: Product) = productCollection.insert(product)
    .map(_ => product)

  def update(id: String, product: Product) = productCollection.update(queryById(id), product)
    .map(_ => product)

  def findById(id: String) = productCollection.find(queryById(id)).one[Product]

  def findByNameAndDescriptionSearch(text: String) = productCollection.find(queryByNameAndDescription(text)).cursor[Product].collect[List]()

  def findByCategoryName(categoryName: String) = productCollection.find(queryByCategoryName(categoryName)).cursor[Product].collect[List]()

  def findAll = productCollection.find(emptyQuery).cursor[Product].collect[List]()

  def deleteById(id: String) = productCollection.remove(queryById(id)).map(_ => id)

  //find( { $text: { $search: "java coffee shop" } } )
  private def queryByNameAndDescription(text: String) = BSONDocument("$text" -> BSONDocument("$search" -> BSONString(text)))

  private def queryByCategoryName(categoryName: String) = BSONDocument("category_name" -> BSONString(categoryName))
}
