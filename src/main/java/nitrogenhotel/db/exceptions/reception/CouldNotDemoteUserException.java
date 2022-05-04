package nitrogenhotel.db.exceptions.reception;

/**
 * Thrown when an error occurs while demoting a user from a reception staff.
 */
public class CouldNotDemoteUserException extends Exception {

  public CouldNotDemoteUserException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
