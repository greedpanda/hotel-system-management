package nitrogenhotel.db.exceptions.customer;

/** An exception to be thrown if the customer information is incorrect or missing. */
public class InvalidCustomerInputException extends Exception {

  public InvalidCustomerInputException(String msg) {
    super(msg);
  }

}
