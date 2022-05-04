package nitrogenhotel.db.exceptions.customer;

/** An exception to be thrown when the customer specified can not be added to the database. */
public class CouldNotAddCustomerException extends Exception {

  public CouldNotAddCustomerException(String msg, Throwable e) {
    super(msg, e);
  }
}
