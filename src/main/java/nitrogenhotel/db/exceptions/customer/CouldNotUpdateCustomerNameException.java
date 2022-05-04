package nitrogenhotel.db.exceptions.customer;

/**
 * Thrown when an error occurs while trying to update a customer's name.
 */
public class CouldNotUpdateCustomerNameException extends Exception {

  public CouldNotUpdateCustomerNameException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
