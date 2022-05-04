package nitrogenhotel.db.exceptions.user;

/** An exception to be thrown when the user specified can not be deleted from the database. */
public class CouldNotDeleteUserException extends Exception {

  public CouldNotDeleteUserException(String msg, Throwable e) {
    super(msg, e);
  }
}
