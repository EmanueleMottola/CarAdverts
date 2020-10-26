package services


final case class DuplicateKeyException (private val message: String = "",
                                  private val cause: Throwable = None.orNull) extends Exception (message, cause){

}
