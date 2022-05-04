package nitrogenhotel.db.exceptions.reservation;

/** An exception to be thrown when the reservation specified can not be added to the database. */
public class CouldNotAddReservationException extends Exception {

  public CouldNotAddReservationException(String msg, Throwable e) {
    super(msg, e);
  }
}
