package nitrogenhotel.db.exceptions.user;

/** An exception to be thrown when the user's screenName cannot be updated in the database. */
public class CouldNotUpdateScreenNameException extends Exception {

  public CouldNotUpdateScreenNameException(String msg, Throwable e) {
    super(msg, e);
  }
}
