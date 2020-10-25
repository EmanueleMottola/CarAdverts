package services

import java.sql.Date

import javax.inject.Singleton
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json.OFormat.oFormatFromReadsAndOWrites
import play.api.libs.json.Reads.DefaultSqlDateReads
import play.api.libs.json.Writes.DefaultLocalDateWrites
import play.api.libs.json.{JsPath, JsValue, Json, Reads, Writes}
import services.{Car, Fuel}

@Singleton
case class AdvertsManagement() {
  private val listOfAdverts = collection.mutable.Map[String, Car]("0" -> Car("0", "Audi", "gasoline", 1500, isNew = true, None, None))

  def getListOfAdverts: Iterable[Car] = {
    listOfAdverts.values
  }

  def getAdvertByID(id: String): Car = {
    if (!listOfAdverts.contains(id))
      throw new NoSuchElementException
    else
      listOfAdverts(id)
  }

  def updateAdvertByID(json: JsValue): Unit = {

    val id = (json \ "id").as[String]
    val title = (json \ "title").as[String]
    val fuel = (json \ "fuel").as[String]
    val price = (json \ "price").as[Int]
    val isNew = (json \ "isNew").as[Boolean]
    val mileage = (json \ "mileage").asOpt[Int]
    val first_registration = (json \ "first_registration").asOpt[Date]

    if(!listOfAdverts.contains(id))
      throw new NoSuchElementException
    else {
      if (((isNew && mileage.isDefined) || (isNew && first_registration.isDefined)) ||
        ((!isNew && mileage.isEmpty) || (!isNew && first_registration.isEmpty)))
        throw AdvertException("A new car does not have mileage and date of registration. A used car has them.")
      else
        listOfAdverts.update(id, Car(id, title, fuel, price, isNew, mileage, first_registration))
    }
  }

  def deleteAdvert(id: String): Unit = {
    if (!listOfAdverts.contains(id))
      throw new NoSuchElementException
    else {
      listOfAdverts.remove(id)
    }
  }
}

object  AdvertsManagement {

}


