package nitrogenhotel.db.exceptions.room;

/** Thrown when an error occurs while updating the number of beds. */
public class CouldNotUpdateNbBedsException extends Exception {

  public CouldNotUpdateNbBedsException(String msg, Throwable e) {
    super(msg, e);
  }
}
