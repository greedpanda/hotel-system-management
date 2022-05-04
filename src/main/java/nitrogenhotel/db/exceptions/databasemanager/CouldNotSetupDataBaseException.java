package nitrogenhotel.db.exceptions.databasemanager;

/**
 * Thrown when there was a problem creating the tables or inserting data.
 */
public class CouldNotSetupDataBaseException extends Exception {

  public CouldNotSetupDataBaseException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
