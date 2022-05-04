package nitrogenhotel.db.exceptions.admin;

/**
 * An exception to be thrown when the specific user cannot be promoted to an admin in the database.
 */
public class CouldNotPromoteUserException extends Exception {

  public CouldNotPromoteUserException(String msg, Throwable e) {
    super(msg, e);
  }
}
