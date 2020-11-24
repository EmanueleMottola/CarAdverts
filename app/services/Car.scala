package services


import java.sql.Date

import org.mongodb.scala.bson.collection.immutable.Document
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, JsValue, Reads, Writes}
import services.Car.checkErrorCar


case class Car( val id: String, val title: String, val fuel: String, val price: Int, val is_new: Boolean, val mileage: Option[Int], val firstRegistration: Option[Date]) {

  if (checkErrorCar(fuel, is_new, mileage, firstRegistration))
    throw AdvertException(id)

}

object Car{

  private def checkErrorCar(fuel: String, is_new: Boolean, mileage: Option[Int], firstRegistration: Option[Date]): Boolean = {
    if(!Fuel.isFuelType(fuel)){

      return true

    }

    if (((is_new && mileage!=null) || (is_new && firstRegistration!=null)) ||
      ((!is_new && mileage==null) || (!is_new && firstRegistration==null))) {

      return true
    }

    false

  }


  def jsValueToCar(json: JsValue): Car = {
    val id = (json \ "_id").as[String]
    val title = (json \ "title").as[String]
    val fuel = (json \ "fuel").as[String]
    val price = (json \ "price").as[Int]
    val is_new = (json \ "is_new").as[Boolean]
    val mileage = (json \ "mileage").asOpt[Int]
    val firstRegistration = (json \ "firstRegistration").asOpt[Date]

    val car = new Car(id, title, fuel, price, is_new, mileage, firstRegistration)

    car
  }

  def carToDocument(car: Car): Document = {
    val doc: Document = Document("_id" -> car.id, "title" -> car.title, "fuel" -> car.fuel,
      "price" -> car.price, "is_new" -> car.is_new, "mileage" -> car.mileage, "firstRegistration" -> car.firstRegistration)
    doc
  }


  implicit val advertsWrites: Writes[Car] = (
    (JsPath \ "_id").write[String] and
    (JsPath \ "title").write[String] and
    (JsPath \ "fuel").write[String] and
    (JsPath \ "price").write[Int] and
    (JsPath \ "is_new").write[Boolean] and
    (JsPath \ "mileage").writeNullable[Int] and
    (JsPath \ "firstRegistration").writeNullable[Date]
  )(unlift(Car.unapply))

  implicit val advertsReads: Reads[Car] = (
    (JsPath \ "_id").read[String] and
    (JsPath \ "title").read[String] and
    (JsPath \ "fuel").read[String] and
    (JsPath \ "price").read[Int] and
    (JsPath \ "is_new").read[Boolean] and
    (JsPath \ "mileage").readNullable[Int] and
    (JsPath \ "firstRegistration").readNullable[Date]
  )(Car.apply _)
}

