package services


import java.sql.Date

import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, Reads, Writes}


case class Car(id: String, title: String, fuel: String, price: Int, isNew: Boolean, mileage: Option[Int], first_registration: Option[Date]) {

}

object Car{


  implicit val advertsWrites: Writes[Car] = (
    (JsPath \ "id").write[String] and
    (JsPath \ "title").write[String] and
    (JsPath \ "fuel").write[String] and
    (JsPath \ "price").write[Int] and
    (JsPath \ "isNew").write[Boolean] and
    (JsPath \ "mileage").writeNullable[Int] and
    (JsPath \ "first_registration").writeNullable[Date]
  )(unlift(Car.unapply))

  implicit val advertsReads: Reads[Car] = (
    (JsPath \ "id").read[String] and
    (JsPath \ "title").read[String] and
    (JsPath \ "fuel").read[String] and
    (JsPath \ "price").read[Int] and
    (JsPath \ "isNew").read[Boolean] and
    (JsPath \ "mileage").readNullable[Int] and
    (JsPath \ "first_registration").readNullable[Date]
  )(Car.apply _)
}

