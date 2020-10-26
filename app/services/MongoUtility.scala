package services

import org.mongodb.scala._
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Sorts.descending
import org.mongodb.scala.model.{Filters, Projections}

class MongoUtility {

  private val db = this.connect()
  private val coll = this.getTable(db)


  def connect(): MongoDatabase = {
    // To directly connect to the default server localhost on port 27017
    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("local")

    database
  }


  def getTable(database: MongoDatabase): MongoCollection[Document] = {
    val coll = database.getCollection("Adverts")
//    if (coll.isInstanceOf[MongoCollection]){
//      coll
//    }
//    else {
//      database.createCollection("Advert")
//      database.getCollection("Adverts")
//    }
    coll
  }


  def getEntireCollectionSorted(field: String): Unit = {
    val res = coll.find().collect().subscribe(
      (results: Seq[Document]) => println(s"Found: #${results}")
    )
    res
  }

  def readAdvert(id: String): Unit = {
    val res = coll.find(Filters.exists("i")).sort(descending("i")).first()
    res.subscribe(
      (user: Document) => println(user.toJson()),                         // onNext
      (error: Throwable) => println(s"Query failed: ${error.getMessage}"), // onError
      () => println("Done") )                                              // onComplete
  }

  def insertAdvert(doc: Document): Unit = {
    val observable: Observable[Completed] = coll.insertOne(doc)
    // Explictly subscribe:
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
