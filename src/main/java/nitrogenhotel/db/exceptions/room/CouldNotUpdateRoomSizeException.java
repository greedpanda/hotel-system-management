package nitrogenhotel.db.exceptions.room;

/**
 * Thrown when there was an error while updating the room size.
 */
public class CouldNotUpdateRoomSizeException extends Exception {

  public CouldNotUpdateRoomSizeException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
