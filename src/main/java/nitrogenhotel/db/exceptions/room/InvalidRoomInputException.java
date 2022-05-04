package nitrogenhotel.db.exceptions.room;

/** An exception to be thrown when given a room that is incorrect or missing information. */
public class InvalidRoomInputException extends Exception {

  public InvalidRoomInputException(String msg) {
    super(msg);
  }
}
