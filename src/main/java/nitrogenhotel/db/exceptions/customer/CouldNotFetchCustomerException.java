package nitrogenhotel.db.exceptions.customer;

/** An exception to be thrown if the specified customer cannot be fetched from the database. */
public class CouldNotFetchCustomerException extends Exception {

  public CouldNotFetchCustomerException(String msg, Throwable e) {
    super(msg, e);
  }
}
