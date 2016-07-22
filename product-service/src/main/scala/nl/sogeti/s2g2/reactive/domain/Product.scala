package nl.sogeti.s2g2.reactive.domain

import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONDouble, BSONNumberLike, BSONObjectID}

/**
  * Created by voorenmi on 11-7-2016.
  */
case class Product(id: String = BSONObjectID.generate.stringify, name: String, description: String, price: Float, category_name: String)

object Product {

  implicit object ProductEntityBSONReader extends BSONDocumentReader[Product] {

    def read(doc: BSONDocument): Product =
      Product(
        id = doc.getAs[BSONObjectID]("_id").get.stringify,
        name = doc.getAs[String]("name").get,
        description = doc.getAs[String]("description").get,
        price = doc.getAs[BSONNumberLike]("price").map(_.toFloat).get,
        category_name = doc.getAs[String]("category_name").get
      )
  }

  implicit object ProductEntityBSONWriter extends BSONDocumentWriter[Product] {

    def write(product: Product): BSONDocument =
      BSONDocument(
        "_id" -> BSONObjectID(product.id),
        "name" -> product.name,
        "description" -> product.description,
        "price" -> BSONDouble(product.price),
        "category_name" -> product.category_name
      )
  }

}
