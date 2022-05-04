package nitrogenhotel.db.exceptions.customer;

/**
 * Thrown when an error occurs while trying to update a customer's public ID.
 */
public class CouldNotUpdatePublicIDException extends Exception {

  public CouldNotUpdatePublicIDException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
