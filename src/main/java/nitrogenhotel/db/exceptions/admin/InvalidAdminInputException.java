package nitrogenhotel.db.exceptions.admin;

/** An exception to be thrown if the Admin information is incorrect or missing. */
public class InvalidAdminInputException extends Exception {

  public InvalidAdminInputException(String msg) {
    super(msg);
  }
}
