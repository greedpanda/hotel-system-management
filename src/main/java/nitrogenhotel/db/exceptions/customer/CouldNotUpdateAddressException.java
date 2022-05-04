package nitrogenhotel.db.exceptions.customer;

/**
 * Thrown when an error occurs while trying to update the address of a customer.
 */
public class CouldNotUpdateAddressException extends Exception {

  public CouldNotUpdateAddressException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
