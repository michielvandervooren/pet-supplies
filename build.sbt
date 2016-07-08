lazy val root = project.in( file(".")).aggregate(common, orderService)

lazy val common = project.in( file("common"))

lazy val orderService = project.in( file("order-service")).dependsOn(common)