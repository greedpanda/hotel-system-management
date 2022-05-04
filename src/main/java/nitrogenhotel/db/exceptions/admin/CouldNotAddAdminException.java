package nitrogenhotel.db.exceptions.admin;

/** An exception to be thrown when the specific admin cannot be added to the database. */
public class CouldNotAddAdminException extends Exception {

  public CouldNotAddAdminException(String msg, Throwable e) {
    super(msg, e);
  }
}
