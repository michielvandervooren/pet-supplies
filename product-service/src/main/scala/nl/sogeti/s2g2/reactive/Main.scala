package nl.sogeti.s2g2.reactive

import com.typesafe.config.ConfigFactory
import nl.sogeti.s2g2.reactive.controller.rest.{CategoryResource, ProductResource, RestService}

/**
  * Created by voorenmi on 20-7-2016.
  */
object Main extends App {
  /* config */
  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")
  val name = config.getString("service.name")
  val version = config.getString("service.version")

  /* declare and bootstrap the RestService */
  val service = RestService(name, version, host, port) withResources {
    ProductResource ++ CategoryResource
  }
}
