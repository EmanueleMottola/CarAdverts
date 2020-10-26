package services

object Fuel extends Enumeration{
  type FuelType = Value

  val gasoline: Fuel.Value = Value("gasoline")
  val diesel: Fuel.Value = Value("diesel")

  def isFuelType(s: String): Boolean = values.exists(s == _.toString)

}
