package nitrogenhotel.db.exceptions.room;

/** Thrown when a bad room type is given to the database. */
public class InvalidRoomTypeException extends Exception {

  public InvalidRoomTypeException(String msg) {
    super(msg);
  }
}
