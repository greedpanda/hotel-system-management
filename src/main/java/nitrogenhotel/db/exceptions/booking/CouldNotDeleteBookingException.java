package nitrogenhotel.db.exceptions.booking;

/** An exception to be thrown when the booking specified can not be deleted from the database. */
public class CouldNotDeleteBookingException extends Exception {

  public CouldNotDeleteBookingException(String msg, Throwable e) {
    super(msg, e);
  }
}
