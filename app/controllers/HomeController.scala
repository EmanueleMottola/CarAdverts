package controllers

import javax.inject._
import services.{Car, Fuel}
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json._
import play.api.libs.json.Writes
import play.api.libs.json.Reads

import play.api.mvc.{AbstractController, ControllerComponents}
import services.{Car, Fuel}



/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  private val listOfAdverts = collection.mutable.Map[Int, Car]("1" -> new Car("Audi", Fuel.gasoline, 15000, false))
  //val nameReads: Reads[String] = (JsPath \ "").read[String]

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  implicit val advertWrite = new Writes[Car] {
    def writes(car: Car) = Json.obj(
      "id" -> car.id,
      "title" -> car.getTitle,
      "fuel" -> car.getFuel,
      "price" -> car.getPrice,
      "isNew" -> car.getIsNew
    )
  }

  def getAll = Action {
    Ok(Json.toJson(listOfAdverts.values))
  }

}
