package nitrogenhotel.db.exceptions.room;

/** Thrown when there was an error while adding the room type. */
public class CouldNotAddRoomTypeException extends Exception {

  public CouldNotAddRoomTypeException(String msg, Throwable e) {
    super(msg, e);
  }
}
