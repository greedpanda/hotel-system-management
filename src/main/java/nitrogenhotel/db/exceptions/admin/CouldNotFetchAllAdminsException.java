package nitrogenhotel.db.exceptions.admin;

/** An exception to be thrown when the list of admins cannot be fetched from the database. */
public class CouldNotFetchAllAdminsException extends Exception {

  public CouldNotFetchAllAdminsException(String msg, Throwable e) {
    super(msg, e);
  }
}
