package services

import org.junit.Test
import services.AdvertsManagement.getListOfAdverts

class AdvertsManagementTest {

  @Test
  def getListOfAdvertsTest(field: String): Unit ={

    val d = AdvertsManagement.getListOfAdverts(field)

    field match {
      case "id" => assert()
    }

  }
}
