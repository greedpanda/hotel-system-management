package nitrogenhotel.db.exceptions.databasemanager;

/** An exception to be thrown when the schema specified is not found in the database. */
public class SchemaNotFoundException extends Exception {

  public SchemaNotFoundException(String msg, Throwable e) {
    super(msg, e);
  }
}
