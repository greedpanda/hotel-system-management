package nitrogenhotel.db.exceptions.reception;

/** An exception to be thrown when the reception Staff specified can not be added to the database. */
public class CouldNotAddReceptionStaffException extends Exception {

  public CouldNotAddReceptionStaffException(String msg, Throwable e) {
    super(msg, e);
  }
}
