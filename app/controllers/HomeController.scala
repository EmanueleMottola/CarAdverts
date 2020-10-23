package controllers

import javax.inject._
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import services.{Car, Fuel}
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json._
import play.api.libs.json.Writes
import play.api.libs.json.Reads
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.{Car, Fuel}



/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  private val listOfAdverts = collection.mutable.Map(0 -> Car("0", "Audi", "gasoline", 1500, true, None, None))


  def getAll: Action[AnyContent] = Action {
    Ok(Json.toJson(listOfAdverts.values))
  }

  def readAdv(id: String): Action[AnyContent] = Action {
    Ok(Json.toJson(listOfAdverts(0)))
  }

  def update(id: String): Action[AnyContent] = Action {
    Ok("Json.toJson(listOfAdverts.values)")
  }

  def delete(id: String): Action[AnyContent] = Action {
    Ok("Json.toJson(listOfAdverts.values)")
  }

  def create: Action[AnyContent] = Action {
    Ok("Json.toJson(listOfAdverts.values)")
  }

}
