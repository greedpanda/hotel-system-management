package nitrogenhotel.db.exceptions.room;

/** An exception to be thrown when an error has occurred while adding a room. */
public class CouldNotAddRoomException extends Exception {

  public CouldNotAddRoomException(String msg, Throwable e) {
    super(msg, e);
  }
}
