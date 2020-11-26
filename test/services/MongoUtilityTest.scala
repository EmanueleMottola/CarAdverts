package services

import java.text.SimpleDateFormat

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mongodb.scala.bson.{BsonBoolean, BsonDocument, BsonInt32, BsonString}

class MongoUtilityTest {

  val db = MongoUtility.connect()
  val coll = MongoUtility.getTable(db)

  @Test
  def getEntireCollectionSortedTest(): Unit = {

    val pattern: String = "yyyy-dd-mm'T'hh:mm:ssXXX"
    val simpleDateFormat: SimpleDateFormat = new SimpleDateFormat(pattern)


    val doc0 = BsonDocument(
      "_id" -> BsonString("0"),
      "title" -> BsonString("Audi A5"),
      "fuel" -> BsonString("gasoline"),
      "price" -> BsonInt32(21500),
      "isNew" -> BsonBoolean(false),
      "mileage" -> BsonInt32(57000),
      "firstRegistration" -> "2019-12-20T00:00:00Z"
    )
    val doc1 = BsonDocument(
      "_id" -> BsonString("1"),
      "title" -> BsonString("Fiat Panda"),
      "fuel" -> BsonString("gasoline"),
      "price" -> BsonInt32(6500),
      "isNew" -> BsonBoolean(false),
      "mileage" -> BsonInt32(107000),
      "firstRegistration" -> "2018-11-14T00:00:00Z"
    )
    val doc2 = BsonDocument(
      "_id" -> BsonString("2"),
      "title" -> BsonString("BMW X6"),
      "fuel" -> BsonString("diesel"),
      "price" -> BsonInt32(13500),
      "isNew" -> BsonBoolean(false),
      "mileage" -> BsonInt32(127000),
      "firstRegistration" -> "2017-01-14T00:00:00Z"
    )

    val mySeqID = MongoUtility.getEntireCollectionSorted("_id")
    assertEquals(doc0, mySeqID(0))
    assertEquals(doc1, mySeqID(1))
    assertEquals(doc2, mySeqID(2))

    val mySeqTitle = MongoUtility.getEntireCollectionSorted("title")
    assertEquals(doc0, mySeqTitle(0))
    assertEquals(doc2, mySeqTitle(1))
    assertEquals(doc1, mySeqTitle(2))

    val mySeqFuel = MongoUtility.getEntireCollectionSorted("fuel")
    assertEquals(doc2, mySeqFuel(0))

    val mySeqPrice = MongoUtility.getEntireCollectionSorted("price")
    assertEquals(doc1, mySeqPrice(0))
    assertEquals(doc2, mySeqPrice(1))
    assertEquals(doc0, mySeqPrice(2))

    val mySeqisNew = MongoUtility.getEntireCollectionSorted("isNew")
    assertEquals(doc0, mySeqisNew(0))
    assertEquals(doc1, mySeqisNew(1))
    assertEquals(doc2, mySeqisNew(2))

    val mySeqMileage = MongoUtility.getEntireCollectionSorted("mileage")
    assertEquals(doc0, mySeqMileage(0))
    assertEquals(doc1, mySeqMileage(1))
    assertEquals(doc2, mySeqMileage(2))

    val mySeqfirstRegistration = MongoUtility.getEntireCollectionSorted("firstRegistration")
    assertEquals(doc2, mySeqfirstRegistration(0))
    assertEquals(doc1, mySeqfirstRegistration(1))
    assertEquals(doc0, mySeqfirstRegistration(2))

  }
}

