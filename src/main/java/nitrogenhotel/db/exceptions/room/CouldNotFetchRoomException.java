package nitrogenhotel.db.exceptions.room;

/** An exception to be thrown when the room specified can not be fetched from the database. */
public class CouldNotFetchRoomException extends Exception {

  public CouldNotFetchRoomException(String msg, Throwable e) {
    super(msg, e);
  }
}
