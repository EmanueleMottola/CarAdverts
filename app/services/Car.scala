package services

import Fuel.Fuel

class Car(title: String, fuel: String, price: Int, isNew: Boolean) {
  var id = Car.newIdNum()

  def getTitle: String = {
    return title
  }

  def getFuel: String = {
    return fuel
  }

  def getPrice: Int = {
    return price
  }

  def getIsNew: Boolean = {
    return isNew
  }

}

object Car{
  private var idCar = 0
  private def newIdNum() = {idCar +=1; idCar}
}

