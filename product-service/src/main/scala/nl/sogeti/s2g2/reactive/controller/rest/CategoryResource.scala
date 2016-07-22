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
    pathEnd {
      post {
        entity(as[Category]) { order =>
          complete(productService.createCategory(order))
        }
      } ~
      get {
        complete(productService.findAllCategories)
      }
    } ~
    path(Segment) { name =>
      get {
        complete(productService.findCategoryByName(name))
      } ~
      put {
        entity(as[Category]) { category =>
          complete(productService.updateCategory(name, category))
        }
      } ~
      delete {
        complete(productService.deleteCategory(name))
      }
    }
  }

}
