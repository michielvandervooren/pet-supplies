package nl.sogeti.s2g2.reactive.service

import nl.sogeti.s2g2.reactive.dao.{CategoryDao, ProductDao}
import nl.sogeti.s2g2.reactive.domain.{Category, Product}

import scala.concurrent.Future

/**
  * Created by voorenmi on 14-7-2016.
  */
class ProductService(database: String, servers: Seq[String]) {

  val productDao = new ProductDao(database, servers)
  val categoryDao = new CategoryDao(database, servers)


  /* category service methods */
  def createCategory(category: Category): Future[Category] = categoryDao.insert(category)

  def updateCategory(name: String, category: Category): Future[Category] = categoryDao.update(name, category)

  def deleteCategory(name: String): Future[String] = categoryDao.deleteByName(name)

  def findAllCategories: Future[List[Category]] = categoryDao.findAll

  def findCategoryByName(name: String): Future[Option[Category]] = categoryDao.findByName(name)


  /* product service methods */
  def createProduct(product: Product): Future[Product] = productDao.insert(product)

  def updateProduct(id: String, product: Product): Future[Product] = productDao.update(id, product)

  def deleteProduct(id: String): Future[String] = productDao.deleteById(id)

  def findAllProducts: Future[List[Product]] = productDao.findAll

  def findProductById(id: String): Future[Option[Product]] = productDao.findById(id)

  def findProductsByCategory(category: String): Future[List[Product]] = productDao.findByCategoryName(category)

  def searchProducts(search: String): Future[List[Product]] = productDao.findByNameAndDescriptionSearch(search)

}
