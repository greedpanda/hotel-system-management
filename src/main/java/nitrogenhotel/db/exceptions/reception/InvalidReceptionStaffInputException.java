package nitrogenhotel.db.exceptions.reception;

/** An exception to be thrown if the reception staff information is incorrect or missing. */
public class InvalidReceptionStaffInputException extends Exception {

  public InvalidReceptionStaffInputException(String msg) {
    super(msg);
  }
}
