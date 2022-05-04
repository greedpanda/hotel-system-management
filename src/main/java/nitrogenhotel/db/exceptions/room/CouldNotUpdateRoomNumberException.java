package nitrogenhotel.db.exceptions.room;

/**
 * Thrown when there was an error while updating the room number.
 */
public class CouldNotUpdateRoomNumberException extends Exception {

  public CouldNotUpdateRoomNumberException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
