package nitrogenhotel.db.entries;

/**
 * This class is used to transfer user data to the database and from the database module to the
 * business layer.
 */
public class User {
  private String userName;
  private String screenName;
  private String password;

  /**
   * Creates a new user instance. The parameters are either the full user attributes or the key
   * (username) of the specified user.
   */
  public User(String userName, String screenName, String password) {
    this.userName = userName;
    this.screenName = screenName;
    this.password = password;
  }

  public User(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
