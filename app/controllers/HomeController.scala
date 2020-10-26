package controllers

import javax.inject._
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import services.{AdvertException, AdvertsManagement, Car, DuplicateKeyException, Fuel}
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json._
import play.api.libs.json.Writes
import play.api.libs.json.Reads
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request, request}



/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  var adverts = new AdvertsManagement

  def untrail(path: String) = Action {
    MovedPermanently("/" + path)
  }

  def getAll: Action[AnyContent] = Action {

    request: Request[AnyContent] =>
      val parameters: Map[String, Seq[String]] = request.queryString
      val flatMap: Map[String, String] = parameters.map { case (k, v) => k -> v.mkString } //flat, only one String

    try{
      Ok(Json.toJson(adverts.getListOfAdverts(flatMap)))
    }
    catch {
      case ex: IllegalArgumentException => {
        Status(404)("Too many query parameters. Required only \"sortBy\" parameter.")
      }
      case ex1: NoSuchFieldException => {
        Status(404)("The field required for sorting is wrong.")
      }
    }
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
              Status(404)("Impossible to update: Resource not found for the requested ID")
            }
            case ex1: AdvertException => {
              Status(404)("Impossible to update: Error in advert content.")
            }
          }
        }
        }
        .getOrElse{
          BadRequest("Expecting application/json request body")
        }
  }

  def delete(id: String): Action[AnyContent] = Action {
    try{
      adverts.deleteAdvert(id)
      Ok("Correctly deleted!")
    }
    catch{
      case ex: NoSuchElementException => {
        Status(404)("Impossible to Delete: Resource not found for the requested ID")
      }
    }
  }

  def create: Action[AnyContent] = Action {
    request: Request[AnyContent] =>
      val body: AnyContent = request.body
      val jsonBody: Option[JsValue] = body.asJson

      jsonBody.map{
        json => {
          try{
            adverts.createAdvert(json)
            Ok("Advert created correctly!")
          }
          catch {
            case ex: DuplicateKeyException => {
              Status(404)("Impossible to create: Resource with same ID already there.")
            }
            case ex1: AdvertException => {
              Status(404)("Impossible to create: Error in advert content.")
            }
          }
        }
      }
        .getOrElse{
          BadRequest("Expecting application/json request body")
        }
  }

}
