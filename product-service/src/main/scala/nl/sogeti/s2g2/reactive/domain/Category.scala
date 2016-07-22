package nl.sogeti.s2g2.reactive.domain

import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

/**
  * Created by voorenmi on 13-7-2016.
  */
case class Category(name: String, description: String)

object Category {

  implicit object CategoryReader extends BSONDocumentReader[Category] {
    def read(doc: BSONDocument): Category =
      Category(
        name = doc.getAs[String]("_id").get,
        description = doc.getAs[String]("description").get
      )
  }

  implicit object CategoryWriter extends BSONDocumentWriter[Category] {
    def write(category: Category): BSONDocument =
      BSONDocument(
        "_id" -> category.name.toLowerCase,
        "description" -> category.description
      )
  }

}
