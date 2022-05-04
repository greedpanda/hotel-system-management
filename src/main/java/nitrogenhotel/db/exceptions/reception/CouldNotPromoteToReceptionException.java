package nitrogenhotel.db.exceptions.reception;

/**
 * Thrown when an error occurs while promoting an existing user.
 */
public class CouldNotPromoteToReceptionException extends Exception {

  public CouldNotPromoteToReceptionException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
