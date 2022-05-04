package nitrogenhotel.db.exceptions.booking;

/** An exception to be thrown when the list of bookings cannot be fetched from the database. */
public class CouldNotFetchAllBookingException extends Exception {

  public CouldNotFetchAllBookingException(String msg, Throwable e) {
    super(msg, e);
  }
}
