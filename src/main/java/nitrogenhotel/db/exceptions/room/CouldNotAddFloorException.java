package nitrogenhotel.db.exceptions.room;

/** Thrown when an error occurs while adding a floor. */
public class CouldNotAddFloorException extends Exception {

  public CouldNotAddFloorException(String msg, Throwable e) {
    super(msg, e);
  }
}
