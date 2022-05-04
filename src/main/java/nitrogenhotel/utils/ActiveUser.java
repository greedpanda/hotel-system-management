package nitrogenhotel.utils;

import nitrogenhotel.db.dataaccessobjects.AdminDao;
import nitrogenhotel.db.entries.NoUser;
import nitrogenhotel.db.entries.User;
import org.tinylog.Logger;

/**
 * The ActiveUser static class will hold the global state of the currently logged in user.
 */
public class ActiveUser {
  private static User user = new NoUser();
  private static UserPrivileges privileges = UserPrivileges.NONE;

  /** Set User as active. */
  public static void set(User user) {
    try { // We assume db has unique usernames
      AdminDao adminDao = new AdminDao();
      boolean userIsAdmin = adminDao.getAll().stream()
              .anyMatch(admin -> user.getUserName().equals(admin.getUserName()));

      ActiveUser.user = user;
      ActiveUser.privileges = userIsAdmin ? UserPrivileges.ADMIN : UserPrivileges.RECEPTION;
    } catch (Exception e) {
      Logger.warn(e);
    }
  }

  public static User get() {
    return user;
  }

  public static UserPrivileges privileges() {
    return privileges;
  }

  public static void clear() {
    user = new NoUser();
    privileges = UserPrivileges.NONE;
  }
}
