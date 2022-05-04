package nitrogenhotel.db.exceptions.admin;

/** An exception to be thrown if the specific admin cannot be fetched from the database. */
public class CouldNotFetchAdminException extends Exception {

  public CouldNotFetchAdminException(String msg, Throwable e) {
    super(msg, e);
  }
}
