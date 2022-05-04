package nitrogenhotel.db.exceptions.user;

/** An exception to be thrown when the user specified can not be fetched from the database. */
public class CouldNotFetchUserException extends Exception {

  public CouldNotFetchUserException(String msg, Throwable e) {
    super(msg, e);
  }
}
