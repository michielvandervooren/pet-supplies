package nl.sogeti.s2g2.reactive.controller.rest

import akka.http.scaladsl.server._
import com.typesafe.config.ConfigFactory
import nl.sogeti.s2g2.reactive.domain.{Category, Product}
import nl.sogeti.s2g2.reactive.service.ProductService

import scala.collection.JavaConverters._
/**
  * Created by voorenmi on 20-7-2016.
  */
object CategoryResource extends Resource {

  val config = ConfigFactory.load()

  val database = config.getString("mongodb.database")
  val servers = config.getStringList("mongodb.servers").asScala

  val productService = new ProductService(database, servers)

  def routes: Route = pathPrefix("categories") {
    crudRoutesFor[Category](all = productService.findAllCategories,
                            read = productService.findCategoryByName,
                            create = productService.createCategory,
                            update = productService.updateCategory,
                            remove = productService.deleteCategory)
  }
}
