package nitrogenhotel.db.exceptions.databasemanager;

/** An exception to be thrown when the database manager fails to connect to the database server. */
public class ConnectionException extends Exception {

  public ConnectionException(String msg, Throwable e) {
    super(msg, e);
  }
}
