package services

import org.junit.Assert.{assertEquals, assertFalse, assertTrue}
import org.junit.Test
import org.mongodb.scala.bson.{BsonBoolean, BsonDocument, BsonInt32, BsonString}

class MongoUtilityTest {

  @Test
  def modifyAdvertTest(): Unit = {

    val expectedAdvert: BsonDocument = BsonDocument(
      "_id" -> BsonString("-1"),
      "title" -> BsonString("Audi A5"),
      "fuel" -> BsonString("gasoline"),
      "price" -> BsonInt32(21500),
      "isNew" -> BsonBoolean(false),
      "mileage" -> BsonInt32(57000),
      "firstRegistration" -> BsonString("2019-12-20T00:00:00Z")
    )

    MongoUtility.insertAdvert(expectedAdvert)

    val actualAdvert = MongoUtility.getAdvertById("-1")

    assertEquals("Insertion function broken, or getAdvertByID broken",
      expectedAdvert, actualAdvert)


    val expectedAdvertModified: BsonDocument = BsonDocument(
      "_id" -> BsonString("-1"),
      "title" -> BsonString("Audi A5"),
      "fuel" -> BsonString("diesel"),
      "price" -> BsonInt32(21500),
      "isNew" -> BsonBoolean(false),
      "mileage" -> BsonInt32(57000),
      "firstRegistration" -> BsonString("2019-12-20T00:00:00Z")
    )
    val expectedAdvertModifiedInesistent: BsonDocument = BsonDocument(
      "_id" -> BsonString("-2"),
      "title" -> BsonString("Audi A5"),
      "fuel" -> BsonString("diesel"),
      "price" -> BsonInt32(21500),
      "isNew" -> BsonBoolean(false),
      "mileage" -> BsonInt32(57000),
      "firstRegistration" -> BsonString("2019-12-20T00:00:00Z")
    )

    val idPresent: Boolean = MongoUtility.modifyAdvert(expectedAdvertModified)
    val idNotPresent: Boolean = MongoUtility.modifyAdvert(expectedAdvertModifiedInesistent)

    assertTrue("Failed because advert is not there even if expected",idPresent)
    assertFalse("Failed because advert is there even if unexpected",idNotPresent)

    val actualAdvertModified = MongoUtility.getAdvertById("-1")
    val actualAdvertModifiedInesistent = MongoUtility.getAdvertById("-2")

    assertEquals("Advert modified is not equal to the one expected.", expectedAdvertModified, actualAdvertModified)
    assertEquals("Test for modification of an inesistent document failed.", BsonDocument(), actualAdvertModifiedInesistent)

    MongoUtility.removeAdvert("-1")

  }


  @Test
  def removeAdvertTest(): Unit = {

    // check that the advert is in the database
    val idPresent: String = "0"
    val idNotPresent: String = "-1"

    val expectedAdvert1: BsonDocument = BsonDocument(
      "_id" -> BsonString("0"),
      "title" -> BsonString("Audi A5"),
      "fuel" -> BsonString("gasoline"),
      "price" -> BsonInt32(21500),
      "isNew" -> BsonBoolean(false),
      "mileage" -> BsonInt32(57000),
      "firstRegistration" -> BsonString("2019-12-20T00:00:00Z")
    )
    val expectedAdvert2 : BsonDocument = BsonDocument()

    val actualAdvert1 = MongoUtility.getAdvertById(idPresent)
    val actualAdvert2 = MongoUtility.getAdvertById(idNotPresent)

    assertEquals(expectedAdvert1, actualAdvert1)
    assertEquals(expectedAdvert2, actualAdvert2)

    MongoUtility.removeAdvert(idPresent)
    MongoUtility.removeAdvert(idNotPresent)

    // check neither of expectedAdvert1 and expectedAdvert2 is in the db
    val expectedAdvert3 = MongoUtility.getAdvertById(idPresent)
    val expectedAdvert4 = MongoUtility.getAdvertById(idNotPresent)

    assertEquals(BsonDocument(), expectedAdvert3)
    assertEquals(BsonDocument(), expectedAdvert4)

    MongoUtility.insertAdvert(expectedAdvert1)

  }


  @Test
  def insertAdvertTest(): Unit = {
    val expectedAdvert1: BsonDocument = BsonDocument(
      "_id" -> BsonString("-1"),
      "title" -> BsonString("Audi A5"),
      "fuel" -> BsonString("gasoline"),
      "price" -> BsonInt32(21500),
      "isNew" -> BsonBoolean(false),
      "mileage" -> BsonInt32(57000),
      "firstRegistration" -> BsonString("2019-12-20T00:00:00Z")
    )

    val expectedAdvert2: BsonDocument = BsonDocument(
      "_id" -> BsonString("0"),
      "title" -> BsonString("Audi A5"),
      "fuel" -> BsonString("gasoline"),
      "price" -> BsonInt32(21500),
      "isNew" -> BsonBoolean(false),
      "mileage" -> BsonInt32(57000),
      "firstRegistration" -> BsonString("2019-12-20T00:00:00Z")
    )

    val actualMessage1: Boolean = MongoUtility.insertAdvert(expectedAdvert1)
    val actualMessage2: Boolean = MongoUtility.insertAdvert(expectedAdvert2)
    val expectedMessage1: Boolean = true
    val expectedMessage2: Boolean = false

    assertEquals(expectedMessage1, actualMessage1)
    assertEquals(expectedMessage2, actualMessage2)

    val actualAdvert: BsonDocument = MongoUtility.getAdvertById("-1")

    assertEquals(expectedAdvert1, actualAdvert)

    MongoUtility.removeAdvert("-1")
  }

  @Test
  def getAdvertByIDTest(): Unit = {

    val id1: String = "0"
    val id2: String = "-1"

    val actualAdvert1: BsonDocument = MongoUtility.getAdvertById(id1)
    val actualAdvert2: BsonDocument = MongoUtility.getAdvertById(id2)

    val expectedAdvert1: BsonDocument = BsonDocument(
      "_id" -> BsonString("0"),
      "title" -> BsonString("Audi A5"),
      "fuel" -> BsonString("gasoline"),
      "price" -> BsonInt32(21500),
      "isNew" -> BsonBoolean(false),
      "mileage" -> BsonInt32(57000),
      "firstRegistration" -> BsonString("2019-12-20T00:00:00Z")
    )
    val expectedAdvert2: BsonDocument = BsonDocument()

    assertEquals(expectedAdvert1, actualAdvert1)
    assertEquals(expectedAdvert2, actualAdvert2)

  }

  @Test
  def getEntireCollectionSortedTest(): Unit = {

    // checking based on _id
    val ordID: Ordering[String] = Ordering[String]
    val seqID = MongoUtility.getEntireCollectionSorted("_id")

    val resID = seqID match {
      case Seq() => true
      case Seq(_) => true
      case _ => seqID.sliding(2).forall { case Seq(x,y) => ordID.lt(x.get("_id").toString,y.get("_id").toString)}
    }
    assertTrue(resID)

    // checking based on title
    val ordTitle: Ordering[String] = Ordering[String]
    val seqTitle = MongoUtility.getEntireCollectionSorted("title")

    val resTitle = seqTitle match {
      case Seq() => true
      case Seq(_) => true
      case _ => seqTitle.sliding(2).forall { case Seq(x,y) => ordTitle.lteq(x.get("title").toString,y.get("title").toString)}
    }
    assertTrue(resTitle)

    // checking based on fuel
    val ordFuel: Ordering[String] = Ordering[String]
    val seqFuel = MongoUtility.getEntireCollectionSorted("fuel")

    val resFuel = seqFuel match {
      case Seq() => true
      case Seq(_) => true
      case _ => seqFuel.sliding(2).forall { case Seq(x,y) => ordFuel.lteq(x.get("fuel").toString,y.get("fuel").toString)}
    }
    assertTrue(resFuel)


    // checking based on price
    val ordPrice: Ordering[Int] = Ordering[Int]
    val seqPrice = MongoUtility.getEntireCollectionSorted("price")

    val resPrice = seqPrice match {
      case Seq() => true
      case Seq(_) => true
      case _ => seqPrice.sliding(2).forall { case Seq(x,y) => ordPrice.lteq(x.get("price").asInt32().getValue, y.get("price").asInt32().getValue)}
    }
    assertTrue(resPrice)

    // checking based on isNew
    val ordIsNew: Ordering[Boolean] = Ordering[Boolean]
    val seqIsNew = MongoUtility.getEntireCollectionSorted("isNew")

    val resIsNew = seqIsNew match {
      case Seq() => true
      case Seq(_) => true
      case _ => seqIsNew.sliding(2).forall { case Seq(x,y) => ordIsNew.lteq(x.get("isNew").asBoolean().getValue,y.get("isNew").asBoolean().getValue)}
    }
    assertTrue(resIsNew)


    // checking based on mileage
    val ordMileage: Ordering[Int] = Ordering[Int]
    val seqMileage = MongoUtility.getEntireCollectionSorted("mileage")

     val resMileage = seqMileage match {
      case Seq() => true
      case Seq(_) => true
      case _ => seqMileage.sliding(2).forall { case Seq(x,y) => ordMileage.lteq(x.get("mileage").asInt32().getValue,y.get("mileage").asInt32().getValue)}
    }
    assertTrue(resMileage)

    // checking based on firstRegistration
    val ordFirstRegistration: Ordering[String] = Ordering[String]
    val seqFirstRegistration = MongoUtility.getEntireCollectionSorted("firstRegistration")

    val resFirstRegistration = seqFirstRegistration match {
      case Seq() => true
      case Seq(_) => true
      case _ => seqFirstRegistration.sliding(2).forall { case Seq(x,y) => ordFirstRegistration.lteq(x.get("firstRegistration").toString,y.get("firstRegistration").toString)}
    }
    assertTrue(resFirstRegistration)

  }


}

