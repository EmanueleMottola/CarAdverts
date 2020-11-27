package services

import org.junit.Assert.assertTrue
import org.junit.Test

class MongoUtilityTest {

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

