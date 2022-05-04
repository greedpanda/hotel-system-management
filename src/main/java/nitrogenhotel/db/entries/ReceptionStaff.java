package nitrogenhotel.db.entries;

/**
 * A class that represents the reception staff table.
 */
public class ReceptionStaff extends User {

  public ReceptionStaff(String userName, String screenName, String password) {
    super(userName, screenName, password);
  }

  public ReceptionStaff(String userName) {
    super(userName);
  }
}
