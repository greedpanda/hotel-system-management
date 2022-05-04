package nitrogenhotel.db.exceptions.room;

/** An exception to be thrown when the list of room types can not be fetched from the database. */
public class CouldNotFetchAllRoomTypesException extends Exception {

  public CouldNotFetchAllRoomTypesException(String msg, Throwable e) {
    super(msg, e);
  }
}
