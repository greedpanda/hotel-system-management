package nitrogenhotel.db.exceptions.admin;

/** An exception to be thrown when the specific admin cannot be deleted from the database. */
public class CouldNotDeleteAdminException extends Exception {

  public CouldNotDeleteAdminException(String msg, Throwable e) {
    super(msg, e);
  }
}
