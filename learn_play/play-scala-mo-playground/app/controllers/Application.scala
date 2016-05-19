package controllers

import play.api.libs.json.JsValue
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getName() = Action {
    Ok("test")
  }

  def parseJson(json: JsValue) = Action { implicit request => ;
    println("json = " + json)

    val name = (json \ "name").asOpt[String].get
    Ok("name : " + name)
  }
}

case class Person(name: String, country: String, id: Int)