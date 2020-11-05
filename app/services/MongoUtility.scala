package services

import java.util.NoSuchElementException

import org.mongodb.scala._
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Projections.excludeId
import org.mongodb.scala.model.Sorts.ascending
import org.mongodb.scala.model.{Filters, Projections}
import play.api.libs.json.Json

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class MongoUtility {

  private val db = this.connect()
  private val coll = this.getTable(db)


  def connect(): MongoDatabase = {
    // To directly connect to the default server localhost on port 27017
    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("Adverts")

    database
  }


  def getTable(database: MongoDatabase): MongoCollection[Document] = {
    val coll = database.getCollection("adverts")
    coll
  }


  def getEntireCollectionSorted(field: String): List[Car] = {

    val res = Await.result(coll.find[Document]().projection(excludeId()).sort(ascending(field)).toFuture(), 2.minutes)

    var iterCar = List[Car]()
    res.foreach(doc => {
        val json = doc.toJson()
        val car: Car = Car.jsValueToCar(Json.parse(json))
        iterCar = iterCar.++(Iterator(car))
    })
    iterCar
  }

  def readAdvert(id: String): Car = {
    try{
      val ris = Await.result(coll.find[Document]({Filters.equal("id", id)}).first().toFuture(), 2.minutes)
      val car: Car = Car.jsValueToCar(Json.parse(ris.toJson()))
      car
    }
    catch {
      case ex: NullPointerException =>
        throw new NoSuchElementException
    }
  }

  def insertAdvert(doc: Document): Unit = {
    Await.result(coll.insertOne(doc).toFuture, 2.minutes)
  }

  def modifyAdvert(document: Document): Unit = {
    val record = coll
      .find()
      .projection(
        Projections
          .fields(Projections.include("offset"), Projections.excludeId()))
      .first()

  }

}
