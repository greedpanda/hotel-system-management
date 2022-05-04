package nitrogenhotel.db.interfaces;

import nitrogenhotel.db.entries.User;

/** This interface defines the updates related to the User table in the database. */
public interface IuserDao {
  boolean updateScreenName(User u, String screenName) throws Exception;

  boolean updatePassword(User u, String password) throws Exception;
}
