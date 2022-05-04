package nitrogenhotel.db.entries;

/** Represents the Administrator view in the database. */
public class Admin extends User {

  public Admin(String userName, String screenName, String password) {
    super(userName, screenName, password);
  }

  public Admin(String userName) {
    super(userName);
  }
}
