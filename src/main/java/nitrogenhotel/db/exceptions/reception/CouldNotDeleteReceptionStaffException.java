package nitrogenhotel.db.exceptions.reception;

/** An exception to be thrown when the reception staff specified can not be deleted from the database. */
public class CouldNotDeleteReceptionStaffException extends Exception {

  public CouldNotDeleteReceptionStaffException(String msg, Throwable e) {
    super(msg, e);
  }
}
