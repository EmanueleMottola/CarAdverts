package services

import org.mongodb.scala._

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


  def getEntireCollectionSorted(field: String): FindObservable[Document] = {
    val res = coll.find()
    res
  }

  def insertAdvert(document: Document): SingleObservable[Completed] = {
    coll.insertOne(document)
  }








}
