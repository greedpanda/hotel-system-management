package nitrogenhotel.db.exceptions.user;

/** An exception to be thrown when the list of users can not fetched from the database. */
public class CouldNotFetchAllUsersException extends Exception {

  public CouldNotFetchAllUsersException(String msg, Throwable e) {
    super(msg, e);
  }
}
