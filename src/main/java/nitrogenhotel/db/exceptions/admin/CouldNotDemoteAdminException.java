package nitrogenhotel.db.exceptions.admin;

/**
 * An exception to be thrown when the specific admin cannot be demoted to a user in the database.
 */
public class CouldNotDemoteAdminException extends Exception {

  public CouldNotDemoteAdminException(String msg, Throwable e) {
    super(msg, e);
  }
}
