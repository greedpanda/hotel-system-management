package nitrogenhotel.db.exceptions.reception;

/** An exception to be thrown when the list of reception staff cannot be fetched from the database. */
public class CouldNotFetchAllReceptionStaffException extends Exception {

  public CouldNotFetchAllReceptionStaffException(String msg, Throwable e) {
    super(msg, e);
  }
}
