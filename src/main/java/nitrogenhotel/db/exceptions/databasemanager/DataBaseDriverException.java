package nitrogenhotel.db.exceptions.databasemanager;

/** An exception to be thrown when the driver for the database is not found. */
public class DataBaseDriverException extends Exception {

  public DataBaseDriverException(String msg, Throwable e) {
    super(msg, e);
  }
}
