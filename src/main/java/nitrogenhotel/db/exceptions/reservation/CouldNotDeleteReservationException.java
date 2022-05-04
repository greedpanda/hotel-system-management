package nitrogenhotel.db.exceptions.reservation;

/** An exception to be thrown when the reservation specified can not be deleted from the database. */
public class CouldNotDeleteReservationException extends Exception {

  public CouldNotDeleteReservationException(String msg, Throwable e) {
    super(msg, e);
  }
}
