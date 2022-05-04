package nitrogenhotel.db.exceptions.reception;

/** An exception to be thrown when the reception staff specified can not be demoted to a user in the DB. */
public class CouldNotDemoteReceptionStaffException extends Exception {
  public CouldNotDemoteReceptionStaffException(String msg, Throwable e) {
    super(msg, e);
  }
}
