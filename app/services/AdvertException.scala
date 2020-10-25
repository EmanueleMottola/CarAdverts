package services


final case class AdvertException (private val message: String = "",
                                  private val cause: Throwable = None.orNull) extends Exception (message, cause){

}
