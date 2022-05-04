package nitrogenhotel.db.exceptions.user;

/** An exception to be thrown when the user's password cannot be updated in the database. */
public class CouldNotUpdatePasswordException extends Exception {

  public CouldNotUpdatePasswordException(String msg, Throwable e) {
    super(msg, e);
  }
}
