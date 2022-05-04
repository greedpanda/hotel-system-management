package nitrogenhotel.db.exceptions.reservation;

/**
 * Thrown when an error occurs while updating the room of a reservation.
 */
public class CouldNotUpdateRoomForReservationException extends Exception {

  public CouldNotUpdateRoomForReservationException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
