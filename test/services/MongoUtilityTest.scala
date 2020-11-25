package services

import java.text.SimpleDateFormat

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mongodb.scala.bson.{BsonBoolean, BsonDateTime, BsonDocument, BsonInt32, BsonString}

class MongoUtilityTest {

  val db = MongoUtility.connect()
  val coll = MongoUtility.getTable(db)

  @Test
  def getEntireCollectionSortedTest(): Unit = {

    val pattern: String = "yyyy-dd-mm'T'hh:mm:ssXXX"
    val simpleDateFormat: SimpleDateFormat = new SimpleDateFormat(pattern)


    val doc1 = BsonDocument(
      "_id" -> BsonString("0"),
      "title" -> BsonString("Audi"),
      "fuel" -> BsonString("diesel"),
      "price" -> BsonInt32(12000),
      "isNew" -> BsonBoolean(false),
      "mileage" -> BsonInt32(120000),
      "first_registration" -> BsonDateTime(simpleDateFormat.parse("1994-11-11T23:00:00Z"))
    )
    val doc2 = BsonDocument(
      "_id" -> BsonString("1"),
      "title" -> BsonString("BMW"),
      "fuel" -> BsonString("gasoline"),
      "price" -> BsonInt32(30000),
      "isNew" -> BsonBoolean(false),
      "mileage" -> BsonInt32(1050),
      "first_registration" -> BsonDateTime(simpleDateFormat.parse("2020-10-08T22:00:00Z"))
    )
    val doc3 = BsonDocument(
      "_id" -> BsonString("2"),
      "title" -> BsonString("AAA"),
      "fuel" -> BsonString("gasoline"),
      "price" -> BsonInt32(30000),
      "isNew" -> BsonBoolean(false),
      "mileage" -> BsonInt32(1050),
      "first_registration" -> BsonDateTime(simpleDateFormat.parse("2020-10-08T22:00:00Z"))
    )

    val mySeq = MongoUtility.getEntireCollectionSorted("_id")
    assertEquals(mySeq(0), doc1)
    assertEquals(mySeq(1), doc2)
    assertEquals(mySeq(2), doc3)

  }
}

