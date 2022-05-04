package nitrogenhotel.db.exceptions.customer;

/**
 * Thrown when an error occurs while updating the payment method of a customer.
 */
public class CouldNotUpdatePaymentMethodException extends Exception {

  public CouldNotUpdatePaymentMethodException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
