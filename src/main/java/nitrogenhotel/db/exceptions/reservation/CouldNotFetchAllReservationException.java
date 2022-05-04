package nitrogenhotel.db.exceptions.reservation;

/** An exception to be thrown when the list of reservations cannot be fetched from the database. */
public class CouldNotFetchAllReservationException extends Exception {

  public CouldNotFetchAllReservationException(String msg, Throwable e) {
    super(msg, e);
  }
}
