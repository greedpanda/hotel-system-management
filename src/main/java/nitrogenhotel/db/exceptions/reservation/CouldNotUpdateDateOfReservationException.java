package nitrogenhotel.db.exceptions.reservation;

/**
 * Thrown when an error happens while updating the date of a reservation.
 */
public class CouldNotUpdateDateOfReservationException extends Exception {

  public CouldNotUpdateDateOfReservationException(String msg, Throwable cause) {
    super(msg, cause);
  }


}
