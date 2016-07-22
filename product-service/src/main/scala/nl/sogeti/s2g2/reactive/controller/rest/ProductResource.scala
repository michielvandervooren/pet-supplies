package nl.sogeti.s2g2.reactive.controller.rest

import akka.http.scaladsl.model.StatusCodes
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
      post {
        entity(as[Product]) { product =>
          complete(productService.createProduct(product))
        }
      } ~
      get {
        parameters('search.?, 'cat.?) { (search, cat) =>
          (search, cat) match {
            case (Some(text), None) => complete(productService.searchProducts(text))
            case (None, Some(name)) => complete(productService.findProductsByCategory(name))
            case (None, None) => complete(productService.findAllProducts)
            case _ => complete(StatusCodes.BadRequest)
          }
        }
      }
    } ~
    path(Segment) { id =>
      delete {
        complete(productService.deleteProduct(id))
      } ~
      get {
        complete(productService.findProductById(id))
      } ~
      put {
        entity(as[Product]) { product =>
          complete(productService.updateProduct(id, product))
        }
      }
    }
  }
}
