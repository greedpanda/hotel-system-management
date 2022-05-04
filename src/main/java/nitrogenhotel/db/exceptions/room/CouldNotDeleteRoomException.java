package nitrogenhotel.db.exceptions.room;

/** An exception to be thrown when the room specified can not be deleted from the database. */
public class CouldNotDeleteRoomException extends Exception {

  public CouldNotDeleteRoomException(String msg, Throwable e) {
    super(msg, e);
  }
}
