package nitrogenhotel.db.exceptions.reservation;

/**
 * Thrown when an error occurs while updating the customer of a reservation.
 */
public class CouldNotUpdateCustomerForReservationException extends Exception {

  public CouldNotUpdateCustomerForReservationException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
