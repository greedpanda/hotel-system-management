package nitrogenhotel.db.exceptions.room;

/**
 * An exception thrown when there was an error while removing a room type.
 */
public class CouldNotRemoveRoomTypeException extends Exception {

  public CouldNotRemoveRoomTypeException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
