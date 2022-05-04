package nitrogenhotel.db.exceptions.customer;

/** An exception to be thrown when the list of customers cannot be fetched from the database. */
public class CouldNotFetchAllCustomersException extends Exception {

  public CouldNotFetchAllCustomersException(String msg, Throwable e) {
    super(msg, e);
  }
}
