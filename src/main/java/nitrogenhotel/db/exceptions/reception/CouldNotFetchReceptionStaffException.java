package nitrogenhotel.db.exceptions.reception;

/** An exception to be thrown if the specific receptionist cannot be fetched from the database. */
public class CouldNotFetchReceptionStaffException extends Exception {

  public CouldNotFetchReceptionStaffException(String msg, Throwable e) {
    super(msg, e);
  }
}
