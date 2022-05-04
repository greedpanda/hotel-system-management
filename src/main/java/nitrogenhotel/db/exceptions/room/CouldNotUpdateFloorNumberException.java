package nitrogenhotel.db.exceptions.room;

/**
 * An exception thrown when there was a problem updating floor number.
 */
public class CouldNotUpdateFloorNumberException extends Exception {

  public CouldNotUpdateFloorNumberException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
