package nitrogenhotel.db.exceptions.booking;

/** An exception to be thrown when the booking specified can not be added to the database. */
public class CouldNotAddBookingException extends Exception {

  public CouldNotAddBookingException(String msg, Throwable e) {
    super(msg, e);
  }
}
