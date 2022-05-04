package nitrogenhotel.db.exceptions.room;

/**
 * Thrown when an error occurs while fetching all the floors.
 */
public class CouldNotFetchAllFloorException extends Exception {

  public CouldNotFetchAllFloorException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
