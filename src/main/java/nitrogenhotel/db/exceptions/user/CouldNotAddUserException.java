package nitrogenhotel.db.exceptions.user;

/** An exception to be thrown when the user specified can not be added to the database. */
public class CouldNotAddUserException extends Exception {

  public CouldNotAddUserException(String msg, Throwable e) {
    super(msg, e);
  }
}
