lazy val root = project.in( file(".")).aggregate(common, orderService, productService)

lazy val common = project.in( file("common"))

lazy val orderService = project.in( file("order-service")).dependsOn(common)

lazy val productService = project.in( file("product-service")).dependsOn(common)