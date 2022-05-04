package nitrogenhotel.db.interfaces;

import nitrogenhotel.db.entries.Admin;
import nitrogenhotel.db.entries.User;

/** This interface allows to promote an existing user to an admin and demote an admin to a user. */
public interface IadminDao {
  void promoteExistingUser(User obj) throws Exception;

  void demoteAdminToUser(User obj) throws Exception;
}
