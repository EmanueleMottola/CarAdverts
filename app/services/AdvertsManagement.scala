package services

import javax.inject.Singleton
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{JsPath, Reads, Writes}
import services.{Car, Fuel}

@Singleton
case class AdvertsManagement() {
  private val listOfAdverts = collection.mutable.Map[String, Car]("0" -> Car("0", "Audi", "gasoline", 1500, isNew = true, None, None))

  def getListOfAdverts: collection.mutable.Map[String, Car] = {
    listOfAdverts
  }

  def getAdvertByID(id: String): Car = {
    try{
      listOfAdverts(id)
    }
    catch {
      case ex: AdvertException => {
        Car("", "", "", -1, false, None, None)
      }
    }
  }

}

object  AdvertsManagement {



}


