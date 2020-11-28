package services

import com.mongodb.MongoWriteException
import org.mongodb.scala._
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.model.Filters
import org.mongodb.scala.model.Sorts.ascending

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

object MongoUtility {

  // MongoDatabase instance
  val db: MongoDatabase = this.connect()

  // MongoCollection instance
  val coll: MongoCollection[Document] = this.getTable(db)


  /**
   * Connects to MongoDB at http://localhost:27017
   * @return MongoDatabase pointer to the "Adverts" database
   */
  def connect(): MongoDatabase = {
    // To directly connect to the default server localhost on port 27017
    val mongoClient: MongoClient = MongoClient()
    val database: MongoDatabase = mongoClient.getDatabase("Adverts")

    database
  }


  /**
   * Retrieves pointer to the required collection
   * @param database MongoDatabase, returned by connect()
   * @return MongoCollection pointer to the "adverts" collection
   */
  def getTable(database: MongoDatabase): MongoCollection[Document] = {
    val coll = database.getCollection("adverts")
    coll
  }


  /**
   * Query the database, retrieves all the car adverts.
   * @param field String. The field according which to order the documents from the database.
   * @return Seq[String]. Every string is an entry.
   *         The Seq is ordered according to the field.
   */
  def getEntireCollectionSorted(field: String): Seq[BsonDocument] = {

    val res = Await.result(coll.find[Document]().sort(ascending(field)).toFuture(), 2.minutes)
    val seq: Seq[BsonDocument] = res.map(x => x.toBsonDocument)
    seq
  }


  /**
   * Query the database, retrieves advert using the id.
   * @param id String. The id of the advert to retrieve
   * @return BsonDocument containing the advert if present.
   *         Empty BsonDocument otherwise.
   */
  def getAdvertById(id: String): BsonDocument = {

    val ris = Await.result(coll.find[Document]({Filters.equal("_id", id)}).first().toFuture(), 2.minutes)

    if (ris == null)
      BsonDocument()
    else
      ris.toBsonDocument

  }


  /**
   * Inserts an advert in the database
   * @param doc BsonDocument describing the advert.
   * @return Boolean true if success, false otherwise
   */
  def insertAdvert(doc: BsonDocument): Boolean = {
    try{
      Await.result(coll.insertOne(doc).toFuture, 2.minutes)
      true
    }
    catch {
      case ex: MongoWriteException => false
    }
  }


  /**
   * Modifies a BsonDocument given the id
   * @param document BsonDocument, the new update
   * @return true if document is there and correctly updated, false otherwise
   */
  def modifyAdvert(document: BsonDocument): Boolean = {

    val res = Await.result(coll.findOneAndReplace(Filters.equal("_id", document("_id")), document).toFuture(), 2.minutes)

    res match {
      case null => false
      case _ => true
    }

  }


  /**
   * Removes an advert given the id.
   * @param id String, the id of the advert.
   * @return Boolean, true if success, false otherwise.
   */
  def removeAdvert(id: String): Boolean = {
    try {
      Await.result(coll.deleteOne({Filters.equal("_id", id)}).toFuture(), 2.minutes)
      true
    }
    catch {
      case ex: Exception => ex.printStackTrace(); false
    }
  }


}
