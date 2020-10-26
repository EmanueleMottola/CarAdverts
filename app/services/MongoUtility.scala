package services

import org.mongodb.scala._
import org.bson._
import org.mongodb.scala.model.Sorts.ascending

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
    if (coll.isInstanceOf[MongoCollection]){
      coll
    }
    else {
      database.createCollection("Advert")
      database.getCollection("Adverts")
    }
  }










}
