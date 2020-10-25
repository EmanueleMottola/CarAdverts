package controllers

import javax.inject._
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import services.{Car, Fuel}
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json._
import play.api.libs.json.Writes
import play.api.libs.json.Reads
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request, request}
import services.{AdvertsManagement, Car, Fuel}



/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  var adverts = new AdvertsManagement

  def getAll: Action[AnyContent] = Action {
    Ok(Json.toJson(adverts.getListOfAdverts))
  }

  def readAdv(id: String): Action[AnyContent] = Action {

    try{
      Ok(Json.toJson(adverts.getAdvertByID(id)))
    }
    catch {
      case ex: NoSuchElementException => {
        Status(404)("Resource not found for the requested ID")
      }
    }
  }

  def update(id: String): Action[AnyContent] = Action {
    request: Request[AnyContent] =>
      val body: AnyContent = request.body
      val jsonBody: Option[JsValue] = body.asJson

      jsonBody.map{
        json => {
          try{
            adverts.updateAdvertByID(json)
            Ok("Updated correctly!")
          }
          catch {
            case ex: NoSuchElementException => {
              Status(404)("Resource not found for the requested ID")
            }
          }
        }
        }
        .getOrElse{
          BadRequest("Expecting application/json request body")
        }
  }

  def delete(id: String): Action[AnyContent] = Action {
    Ok("Json.toJson(listOfAdverts.values)")
  }

  def create: Action[AnyContent] = Action {
    Ok("Json.toJson(listOfAdverts.values)")
  }

}
