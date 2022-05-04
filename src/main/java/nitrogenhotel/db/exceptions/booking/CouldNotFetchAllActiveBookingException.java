package nitrogenhotel.db.exceptions.booking;

/** An exception to be thrown when the list of bookings cannot be fetched from the database. */
public class CouldNotFetchAllActiveBookingException extends Exception {

  public CouldNotFetchAllActiveBookingException(String msg, Throwable e) {
    super(msg, e);
  }
}
