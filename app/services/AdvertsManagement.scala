package services

import java.sql.Date

import javax.inject.Singleton
import org.mongodb.scala.bson.Document
import play.api.libs.json.JsValue
import play.api.libs.json.Reads.DefaultSqlDateReads


@Singleton
case class AdvertsManagement() {
  private val listOfAdverts = collection.mutable.Map[String, Car]("0" -> Car("0", "Audi", "gasoline", 1500, isNew = true, None, None))
  private val mongoutility = new MongoUtility



  def getListOfAdverts(listOfParam: Map[String, String]): List[Car] = {

    var response: List[Car] = List()

    if(listOfParam.size > 1){
      throw new IllegalArgumentException
    }
    else if (listOfParam.size == 1 && !listOfParam.keySet.contains("sortBy")) {
      throw new IllegalArgumentException
    }
    else if (listOfParam.isEmpty) {
      response = mongoutility.getEntireCollectionSorted("id")
    }
    else {
      listOfParam("sortBy") match {
        case "id" =>
          response = mongoutility.getEntireCollectionSorted("id")
        case "title" =>
          response = mongoutility.getEntireCollectionSorted("title")
        case "fuel" =>
          response = mongoutility.getEntireCollectionSorted("fuel")
        case "price" =>
          response = mongoutility.getEntireCollectionSorted("price")
        case "isNew" =>
          response = mongoutility.getEntireCollectionSorted("isNew")
        case "mileage" =>
          response = mongoutility.getEntireCollectionSorted("mileage")
        case "first_registration" =>
          response = mongoutility.getEntireCollectionSorted("first_registration")
        case _ =>
          throw new NoSuchFieldException()
      }
    }
    response
  }

  def getAdvertByID(id: String): Car = {
    try{
      mongoutility.readAdvert(id)
    }
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
      if(!Fuel.isFuelType(fuel)){

        throw AdvertException("Wrong fuel type.")

      }

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



  def createAdvert(json: JsValue): Unit = {

    try{
      val car: Car = Car.jsValueToCar(json)
      val doc: Document = Car.carToDocument(car)
      mongoutility.insertAdvert(doc)
    }



//    val id = (json \ "id").as[String]
//    val title = (json \ "title").as[String]
//    val fuel = (json \ "fuel").as[String]
//    val price = (json \ "price").as[Int]
//    val isNew = (json \ "isNew").as[Boolean]
//    val mileage = (json \ "mileage").asOpt[Int]
//    val first_registration = (json \ "first_registration").asOpt[Date]
//
//
//    if(listOfAdverts.contains(id))
//
//      throw DuplicateKeyException("Duplicate key")
//
//    else {
//
//      if(!Fuel.isFuelType(fuel)){
//
//          throw AdvertException("Wrong fuel type.")
//
//        }
//
//      if (((isNew && mileage.isDefined) || (isNew && first_registration.isDefined)) ||
//        ((!isNew && mileage.isEmpty) || (!isNew && first_registration.isEmpty)))
//
//        throw AdvertException("A new car does not have mileage and date of registration. A used car has them.")
//
//      else {
//        val doc: Document = Document("id" -> id, "title" -> title, "fuel" -> fuel,
//          "price" -> 1, "isNew" -> isNew, "mileage" -> mileage, "first_registration" -> first_registration)
//        mongoutility.insertAdvert(doc)
//        listOfAdverts.put(id, Car(id, title, fuel, price, isNew, mileage, first_registration))
//      }
//
//    }

  }
}

object  AdvertsManagement {

}


