package controllers

import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._
import services.AdvertsManagement



/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index(): Action[AnyContent] = Action {
    Ok("")
  }

  def untrail(path: String): Action[AnyContent] = Action {
    MovedPermanently("/" + path)
  }

  def getAllCarAdverts(field: String): Action[AnyContent] = Action {
    Ok(Json.toJson(AdvertsManagement.getListOfAdverts(field)))
  }

  def getCarAdvertByField(id: String): Action[AnyContent] = Action {
    Ok("")
  }

  def updateCarAdvert(id: String): Action[AnyContent] = Action {
    Ok("")
  }

  def deleteCarAdvert(id: String): Action[AnyContent] = Action {
    Ok("")
  }

  def insertCarAdvert(): Action[AnyContent] = Action {
    Ok("")
  }

}
