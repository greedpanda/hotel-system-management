package nitrogenhotel.db.exceptions.booking;

/** An exception to be thrown if the specified booking cannot be fetched from the database. */
public class CouldNotFetchBookingException extends Exception {

  public CouldNotFetchBookingException(String msg, Throwable e) {
    super(msg, e);
  }
}
