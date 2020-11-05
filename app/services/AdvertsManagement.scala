package services

import javax.inject.Singleton
import org.mongodb.scala.bson.Document
import play.api.libs.json.JsValue


@Singleton
case class AdvertsManagement() {
  private val mongoutility = new MongoUtility

  def getListOfAdverts(listOfParam: Map[String, String]): List[Car] = {

    var response: List[Car] = List()

    println(listOfParam)
    if(listOfParam.size > 1){
      throw new IllegalArgumentException
    }
    else if (listOfParam.size == 1 && !listOfParam.keySet.contains("sortBy")) {
      throw new IllegalArgumentException
    }
    else if (listOfParam.isEmpty) {
      response = mongoutility.getEntireCollectionSorted("_id")
    }
    else {
      println(listOfParam("sortBy"))
      listOfParam("sortBy") match {
        case "id" =>
          response = mongoutility.getEntireCollectionSorted("_id")
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
    mongoutility.readAdvert(id)
  }



  def updateAdvertByID(json: JsValue): Unit = {

    val car: Car = Car.jsValueToCar(json)
    val doc: Document = Car.carToDocument(car)
    mongoutility.modifyAdvert(doc)
  }



  def deleteAdvert(id: String): Unit = {
    mongoutility.removeAdvert(id)
  }



  def createAdvert(json: JsValue): Unit = {

    val car: Car = Car.jsValueToCar(json)
    val doc: Document = Car.carToDocument(car)
    mongoutility.insertAdvert(doc)

  }
}

object  AdvertsManagement {

}


