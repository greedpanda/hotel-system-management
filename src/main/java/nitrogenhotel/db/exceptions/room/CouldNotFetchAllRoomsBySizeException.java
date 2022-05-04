package nitrogenhotel.db.exceptions.room;

/** An exception to be thrown when the list of rooms with specified size can not be fetched from the database. */
public class CouldNotFetchAllRoomsBySizeException extends Exception {

  public CouldNotFetchAllRoomsBySizeException(String msg, Throwable e) {
    super(msg, e);
  }
}
