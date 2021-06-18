package controllers

import javax.inject._

import play.api.mvc._

import play.api.db._


@Singleton
class HomeController @Inject()(db: Database, cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {

  /**
   * establishing mysql database connections
   * first we must inject Database to the HomeController class as 'db'
   * then using 'db' we establishin database connection to the database mentioned in application.conf
   * then we can create sql statement
   * finally 'resultset' is a value for execute that sql statement*/
  val connection = db.getConnection()
  val statement = connection.createStatement
  val query = "SELECT * FROM member"
  val resultset = statement.executeQuery(query)

  var i = 0
  var indexArray = new Array[Int](5)
  var namesArray = new Array[String](5)
  var regNoArray = new Array[String](5)

  /**Following will get data from corresponding database position and set those to corresponding arrays*/
  while (resultset.next()) {
    indexArray(i) = resultset.getInt("IndexNo")
    namesArray(i) = resultset.getString("Name")
    regNoArray(i) = resultset.getString("Regno")
    i = i + 1
  }

  /**This is a main home function that passes the data associated within the arrays to the home.scala.html*/
  def home = Action {
    Ok(views.html.home(
      indexArray(0),indexArray(1),indexArray(2),indexArray(3),
      namesArray(0),namesArray(1),namesArray(2),namesArray(3),
      regNoArray(0),regNoArray(1),regNoArray(2),regNoArray(3)))
  }
}