package nitrogenhotel.db.exceptions.customer;

/** An exception to be thrown when the customer specified can not be deleted from the database. */
public class CouldNotDeleteCustomerException extends Exception {

  public CouldNotDeleteCustomerException(String msg, Throwable e) {
    super(msg, e);
  }
}
