package nitrogenhotel.db.exceptions.reservation;

/** An exception to be thrown if the specified reservation cannot be fetched from the database. */
public class CouldNotFetchReservationException extends Exception {

  public CouldNotFetchReservationException(String msg, Throwable e) {
    super(msg, e);
  }
}
