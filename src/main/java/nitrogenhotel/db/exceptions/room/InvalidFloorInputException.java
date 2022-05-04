package nitrogenhotel.db.exceptions.room;

/** Thrown when a bad floor number is given. */
public class InvalidFloorInputException extends Exception {

  public InvalidFloorInputException(String msg) {
    super(msg);
  }
}
