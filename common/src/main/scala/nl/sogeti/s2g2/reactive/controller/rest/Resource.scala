package nl.sogeti.s2g2.reactive.controller.rest

import akka.http.scaladsl.server._
import nl.sogeti.s2g2.reactive.controller.rest.json.JsonSupport

/**
  * Created by voorenmi on 30-5-2016.
  */
trait Resource extends Directives with JsonSupport {
  def routes: Route
  implicit def resourceToList(resource: Resource): List[Resource] = List(resource)
}
