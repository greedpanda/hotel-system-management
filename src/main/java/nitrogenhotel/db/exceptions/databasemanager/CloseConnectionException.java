package nitrogenhotel.db.exceptions.databasemanager;

/** An exception to be thrown when the database manager fails to close the connection. */
public class CloseConnectionException extends Exception {

  public CloseConnectionException(String msg, Throwable e) {
    super(msg, e);
  }
}
