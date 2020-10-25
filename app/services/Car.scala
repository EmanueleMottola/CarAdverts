package services


import java.sql.Date

import Fuel.Fuel
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json.{Format, JsPath, Json, Reads, Writes}


case class Car(id: String, title: String, fuel: String, price: Int, isNew: Boolean, mileage: Option[Int], first_registration: Option[Date]) {

//  def getId: String = {
//    return id
//  }
//
//  def getTitle: String = {
//    return title
//  }
//
//  def getFuel: String = {
//    return fuel
//  }
//
//  def getPrice: Int = {
//    return price
//  }
//
//  def getIsNew: Boolean = {
//    return isNew
//  }
//
//  def getMileage: Option[Int] = if (!getIsNew) mileage else None
//
//  def getFirst_Registration: Option[Date] = if (!getIsNew) first_registration else None

}

object Car{
//  private var idCar = 0
//  private def newIdNum() = {idCar +=1; idCar}

  //implicit val format: Format[Car] = Json.format

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

