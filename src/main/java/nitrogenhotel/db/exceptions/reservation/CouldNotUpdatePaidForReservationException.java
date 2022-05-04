package nitrogenhotel.db.exceptions.reservation;

/**
 * Thrown when an error occurs while updating the payment status of a reservation.
 */
public class CouldNotUpdatePaidForReservationException extends Exception {

  public CouldNotUpdatePaidForReservationException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
