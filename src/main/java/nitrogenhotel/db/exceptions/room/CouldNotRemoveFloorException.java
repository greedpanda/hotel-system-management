package nitrogenhotel.db.exceptions.room;

/**
 * Thrown when there was a problem while removing the room type.
 */
public class CouldNotRemoveFloorException extends Exception {

  public CouldNotRemoveFloorException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
