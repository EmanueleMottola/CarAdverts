package services


import java.sql.Date

import org.mongodb.scala.bson.collection.immutable.Document
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, JsValue, Reads, Writes}
import services.Car.{checkErrorCar}


case class Car(id: String, title: String,var fuel: String, price: Int, var isNew: Boolean, var mileage: Option[Int], var first_registration: Option[Date]) {

  if (checkErrorCar(fuel, isNew, mileage, first_registration))
    throw AdvertException(id)

}

object Car{

  private def checkErrorCar(fuel: String, isNew: Boolean, mileage: Option[Int], first_registration: Option[Date]): Boolean = {
    if(!Fuel.isFuelType(fuel)){

      return true

    }

    if (((isNew && mileage!=null) || (isNew && first_registration!=null)) ||
      ((!isNew && mileage==null) || (!isNew && first_registration==null))) {

      return true
    }

    false

  }


  def jsValueToCar(json: JsValue): Car = {
    val id = (json \ "_id").as[String]
    val title = (json \ "title").as[String]
    val fuel = (json \ "fuel").as[String]
    val price = (json \ "price").as[Int]
    val isNew = (json \ "isNew").as[Boolean]
    val mileage = (json \ "mileage").asOpt[Int]
    val first_registration = (json \ "first_registration").asOpt[Date]

    val car = new Car(id, title, fuel, price, isNew, mileage, first_registration)

    car
  }

  def carToDocument(car: Car): Document = {
    val doc: Document = Document("_id" -> car.id, "title" -> car.title, "fuel" -> car.fuel,
      "price" -> car.price, "isNew" -> car.isNew, "mileage" -> car.mileage, "first_registration" -> car.first_registration)
    doc
  }


  implicit val advertsWrites: Writes[Car] = (
    (JsPath \ "_id").write[String] and
    (JsPath \ "title").write[String] and
    (JsPath \ "fuel").write[String] and
    (JsPath \ "price").write[Int] and
    (JsPath \ "isNew").write[Boolean] and
    (JsPath \ "mileage").writeNullable[Int] and
    (JsPath \ "first_registration").writeNullable[Date]
  )(unlift(Car.unapply))

  implicit val advertsReads: Reads[Car] = (
    (JsPath \ "_id").read[String] and
    (JsPath \ "title").read[String] and
    (JsPath \ "fuel").read[String] and
    (JsPath \ "price").read[Int] and
    (JsPath \ "isNew").read[Boolean] and
    (JsPath \ "mileage").readNullable[Int] and
    (JsPath \ "first_registration").readNullable[Date]
  )(Car.apply _)
}

