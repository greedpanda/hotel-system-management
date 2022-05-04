package nitrogenhotel.db.exceptions.room;

/** An exception to be thrown when the list of rooms can not fetched from the database. */
public class CouldNotFetchAllRoomsException extends Exception {

  public CouldNotFetchAllRoomsException(String msg, Throwable e) {
    super(msg, e);
  }
}
