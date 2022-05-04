package nitrogenhotel.db.exceptions.reservation;

/** An exception to be thrown if the reservation information is incorrect or missing. */
public class InvalidReservationInputException extends Exception {

  public InvalidReservationInputException(String msg) {
    super(msg);
  }
}
