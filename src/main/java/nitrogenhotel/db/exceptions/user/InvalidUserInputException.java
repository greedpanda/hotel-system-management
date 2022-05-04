package nitrogenhotel.db.exceptions.user;

/** An exception to be thrown if the input given through a User is invalid. */
public class InvalidUserInputException extends Exception {

  public InvalidUserInputException(String msg) {
    super(msg);
  }
}
