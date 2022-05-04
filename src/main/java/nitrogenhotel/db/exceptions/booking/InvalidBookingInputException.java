package nitrogenhotel.db.exceptions.booking;

/** An exception to be thrown if the booking information is incorrect or missing. */
public class InvalidBookingInputException extends Exception {

  public InvalidBookingInputException(String msg) {
    super(msg);
  }
}
