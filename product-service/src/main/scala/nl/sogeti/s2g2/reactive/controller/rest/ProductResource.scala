package nl.sogeti.s2g2.reactive.controller.rest

import akka.http.scaladsl.server._
import com.typesafe.config.ConfigFactory
import nl.sogeti.s2g2.reactive.domain.Product
import nl.sogeti.s2g2.reactive.service.ProductService

import scala.collection.JavaConverters._

/**
  * Created by voorenmi on 20-7-2016.
  */
object ProductResource extends Resource {
  val config = ConfigFactory.load()

  val database = config.getString("mongodb.database")
  val servers = config.getStringList("mongodb.servers").asScala

  val productService = new ProductService(database, servers)

  def routes: Route = pathPrefix("products") {
    pathEnd {
      get {
        parameter('search) { search =>
          complete(productService.searchProducts(search))
        } ~
        parameter('cat) { cat =>
          complete(productService.findProductsByCategory(cat))
        }
      }
    } ~
    crudRoutesFor[Product](all = productService.findAllProducts,
      read = productService.findProductById,
      create = productService.createProduct,
      update = productService.updateProduct,
      remove = productService.deleteProduct)
  }
}
